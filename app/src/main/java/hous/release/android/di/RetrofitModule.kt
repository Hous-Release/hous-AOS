package hous.release.android.di

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.android.BuildConfig
import hous.release.data.datasource.LocalPrefTokenDataSource
import hous.release.data.repository.RefreshRepositoryImpl.Companion.EXPIRED_REFRESH_TOKEN
import hous.release.data.repository.RefreshRepositoryImpl.Companion.EXPIRED_TOKEN
import hous.release.domain.repository.RefreshRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_OS_TYPE = "HousOsType"
    private const val HEADER_VERSION = "HousVersion"
    private const val OS_TYPE = "AOS"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NormalType

    @Provides
    @Singleton
    @NormalType
    fun providesInterceptor(
        localPref: SharedPreferences,
        refreshRepository: RefreshRepository,
        localPrefTokenDataSource: LocalPrefTokenDataSource
    ): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(
                request
                    .newBuilder()
                    .addHeader(
                        HEADER_AUTHORIZATION,
                        localPrefTokenDataSource.accessToken
                    )
                    .addHeader(HEADER_OS_TYPE, OS_TYPE)
                    .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                    .build()
            )
            when (response.code) {
                EXPIRED_TOKEN -> {
                    runBlocking {
                        refreshRepository.refreshHousToken()
                            .onSuccess {
                                response = chain.proceed(
                                    request
                                        .newBuilder()
                                        .addHeader(
                                            HEADER_AUTHORIZATION,
                                            localPrefTokenDataSource.accessToken
                                        )
                                        .addHeader(HEADER_OS_TYPE, OS_TYPE)
                                        .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                                        .build()
                                )
                            }
                            .onFailure { throwable ->
                                Timber.d("토큰 갱신 실패 ${throwable.message}")
                                if (throwable is HttpException) {
                                    when (throwable.code()) {
                                        EXPIRED_REFRESH_TOKEN -> {
                                            with(localPref.edit()) {
                                                clear()
                                                commit()
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            response
        }

    @Provides
    @Singleton
    @NormalType
    fun providesOkHttpClient(@NormalType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    @Singleton
    @NormalType
    fun providesRetrofit(@NormalType okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URI)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
}

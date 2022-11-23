package hous.release.android.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.android.BuildConfig
import hous.release.data.datasource.LocalPrefTokenDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RefreshRetrofitModule {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_OS_TYPE = "HousOsType"
    private const val HEADER_VERSION = "HousVersion"
    private const val OS_TYPE = "AOS"
    private const val BEARER = "Bearer "

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RefreshType

    @Provides
    @Singleton
    @RefreshType
    fun providesRefreshInterceptor(
        localPrefTokenDataSource: LocalPrefTokenDataSource
    ): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(
                request
                    .newBuilder()
                    .addHeader(
                        HEADER_AUTHORIZATION,
                        BEARER + localPrefTokenDataSource.accessToken
                    )
                    .addHeader(HEADER_OS_TYPE, OS_TYPE)
                    .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                    .build()
            )
            response
        }

    @Provides
    @Singleton
    @RefreshType
    fun providesRefreshOkHttpClient(@RefreshType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    @Singleton
    @RefreshType
    fun providesRefreshRetrofit(@RefreshType okHttpClient: OkHttpClient): Retrofit =
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

package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.service.AuthService
import hous.release.data.service.EnterRoomService
import hous.release.data.service.ToDoService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesEnterRoomService(retrofit: Retrofit): EnterRoomService =
        retrofit.create(EnterRoomService::class.java)

    @Provides
    @Singleton
    fun providesToDoService(retrofit: Retrofit): ToDoService =
        retrofit.create(ToDoService::class.java)
}

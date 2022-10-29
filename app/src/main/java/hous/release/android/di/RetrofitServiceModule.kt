package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.service.AuthService
import hous.release.data.service.BadgeService
import hous.release.data.service.EnterRoomService
import hous.release.data.service.HousService
import hous.release.data.service.NotificationService
import hous.release.data.service.OurRulesService
import hous.release.data.service.ProfileService
import hous.release.data.service.TodoService
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
    fun provideHousService(retrofit: Retrofit): HousService =
        retrofit.create(HousService::class.java)

    @Provides
    @Singleton
    fun providesOurRulesService(retrofit: Retrofit): OurRulesService =
        retrofit.create(OurRulesService::class.java)

    @Provides
    @Singleton
    fun providesToDoService(retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun providesProfileService(retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

    @Provides
    @Singleton
    fun providesBadgeService(retrofit: Retrofit): BadgeService =
        retrofit.create(BadgeService::class.java)
}

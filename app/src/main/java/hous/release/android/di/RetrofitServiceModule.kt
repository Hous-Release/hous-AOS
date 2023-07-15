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
import hous.release.data.service.RuleService
import hous.release.data.service.PersonalityService
import hous.release.data.service.ProfileService
import hous.release.data.service.RefreshService
import hous.release.data.service.SettingsService
import hous.release.data.service.TodoService
import hous.release.data.service.VersionService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesRefreshService(@RefreshRetrofitModule.RefreshType retrofit: Retrofit): RefreshService =
        retrofit.create(RefreshService::class.java)

    @Provides
    @Singleton
    fun providesAuthService(@RetrofitModule.NormalType retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesEnterRoomService(@RetrofitModule.NormalType retrofit: Retrofit): EnterRoomService =
        retrofit.create(EnterRoomService::class.java)

    @Provides
    @Singleton
    fun provideHousService(@RetrofitModule.NormalType retrofit: Retrofit): HousService =
        retrofit.create(HousService::class.java)

    @Provides
    @Singleton
    fun providesOurRulesService(@RetrofitModule.NormalType retrofit: Retrofit): RuleService =
        retrofit.create(RuleService::class.java)

    @Provides
    @Singleton
    fun providesToDoService(@RetrofitModule.NormalType retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideNotificationService(@RetrofitModule.NormalType retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun providesPersonalityService(@RetrofitModule.NormalType retrofit: Retrofit): PersonalityService =
        retrofit.create(PersonalityService::class.java)

    @Provides
    @Singleton
    fun providesProfileService(@RetrofitModule.NormalType retrofit: Retrofit): ProfileService =
        retrofit.create(ProfileService::class.java)

    @Provides
    @Singleton
    fun providesBadgeService(@RetrofitModule.NormalType retrofit: Retrofit): BadgeService =
        retrofit.create(BadgeService::class.java)

    @Provides
    @Singleton
    fun provideSettingsService(@RetrofitModule.NormalType retrofit: Retrofit): SettingsService =
        retrofit.create(SettingsService::class.java)

    @Provides
    @Singleton
    fun providesVersionService(@RetrofitModule.NormalType retrofit: Retrofit): VersionService =
        retrofit.create(VersionService::class.java)
}

package hous.release.android.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.RuleDataSource
import hous.release.data.datasource.TodoDataSource
import hous.release.data.repository.AuthRepositoryImpl
import hous.release.data.repository.BadgeRepositoryImpl
import hous.release.data.repository.EnterRoomRepositoryImpl
import hous.release.data.repository.HousRepositoryImpl
import hous.release.data.repository.ImageRepositoryImpl
import hous.release.data.repository.NotificationRepositoryImpl
import hous.release.data.repository.PersonalityRepositoryImpl
import hous.release.data.repository.PhotoRepositoryImpl
import hous.release.data.repository.ProfileRepositoryImpl
import hous.release.data.repository.RefreshRepositoryImpl
import hous.release.data.repository.RuleRepositoryImpl
import hous.release.data.repository.SettingsRepositoryImpl
import hous.release.data.repository.TodoRepositoryImpl
import hous.release.data.repository.VersionRepositoryImpl
import hous.release.domain.repository.AuthRepository
import hous.release.domain.repository.BadgeRepository
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.repository.HousRepository
import hous.release.domain.repository.ImageRepository
import hous.release.domain.repository.NotificationRepository
import hous.release.domain.repository.PersonalityRepository
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.repository.ProfileRepository
import hous.release.domain.repository.RefreshRepository
import hous.release.domain.repository.RuleRepository
import hous.release.domain.repository.SettingsRepository
import hous.release.domain.repository.TodoRepository
import hous.release.domain.repository.VersionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository =
        ImageRepositoryImpl(context)

    @Provides
    @Singleton
    fun providesRefreshRepository(refreshRepositoryImpl: RefreshRepositoryImpl): RefreshRepository =
        refreshRepositoryImpl

    @Provides
    @Singleton
    fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun providesEnterRoomRepository(enterRoomRepositoryImpl: EnterRoomRepositoryImpl): EnterRoomRepository =
        enterRoomRepositoryImpl

    @Provides
    @Singleton
    fun provideHousRepository(housRepositoryImpl: HousRepositoryImpl): HousRepository =
        housRepositoryImpl

    @Provides
    @Singleton
    fun providesOurRulesRepository(
        ourRulesDataSource: RuleDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): RuleRepository = RuleRepositoryImpl(ourRulesDataSource, ioDispatcher)

    @Provides
    @Singleton
    fun providesTodoRepository(todoDataSource: TodoDataSource): TodoRepository =
        TodoRepositoryImpl(todoDataSource)

    @Provides
    @Singleton
    fun providesNotificationRepository(notificationRepositoryImpl: NotificationRepositoryImpl): NotificationRepository =
        notificationRepositoryImpl

    @Provides
    @Singleton
    fun providesPersonalityRepository(personalityRepositoryImpl: PersonalityRepositoryImpl): PersonalityRepository =
        personalityRepositoryImpl

    @Provides
    @Singleton
    fun providesProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository =
        profileRepositoryImpl

    @Provides
    @Singleton
    fun providesBadgeRepository(badgeRepositoryImpl: BadgeRepositoryImpl): BadgeRepository =
        badgeRepositoryImpl

    @Provides
    @Singleton
    fun providesSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository =
        settingsRepositoryImpl

    @Provides
    @Singleton
    fun providesVersionRepository(versionRepositoryImpl: VersionRepositoryImpl): VersionRepository =
        versionRepositoryImpl

    @Provides
    @Singleton
    fun providePhotoRepository(rulePhotoRepository: PhotoRepositoryImpl): PhotoRepository =
        rulePhotoRepository
}

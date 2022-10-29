package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.OurRulesDataSource
import hous.release.data.repository.AuthRepositoryImpl
import hous.release.data.repository.EnterRoomRepositoryImpl
import hous.release.data.repository.HousRepositoryImpl
import hous.release.data.repository.NotificationRepositoryImpl
import hous.release.data.repository.OurRulesRepositoryImpl
import hous.release.data.repository.PersonalityRepositoryImpl
import hous.release.data.repository.ProfileRepositoryImpl
import hous.release.data.repository.TodoRepositoryImpl
import hous.release.domain.repository.AuthRepository
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.repository.HousRepository
import hous.release.domain.repository.NotificationRepository
import hous.release.domain.repository.OurRulesRepository
import hous.release.domain.repository.PersonalityRepository
import hous.release.domain.repository.ProfileRepository
import hous.release.domain.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
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
        ourRulesDataSource: OurRulesDataSource
    ): OurRulesRepository =
        OurRulesRepositoryImpl(ourRulesDataSource)

    @Provides
    @Singleton
    fun providesTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository =
        todoRepositoryImpl

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
}

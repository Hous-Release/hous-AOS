package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.*
import hous.release.data.repository.*
import hous.release.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(
        authDataSource: AuthDataSource,
        localPrefSkipTutorialDataSource: LocalPrefSkipTutorialDataSource
    ): AuthRepository =
        AuthRepositoryImpl(authDataSource, localPrefSkipTutorialDataSource)

    @Provides
    @Singleton
    fun providesEnterRoomRepository(enterRoomDataSource: EnterRoomDataSource): EnterRoomRepository =
        EnterRoomRepositoryImpl(enterRoomDataSource)

    @Provides
    @Singleton
    fun provideHousRepository(housDataSource: HousDataSource): HousRepository =
        HousRepositoryImpl(housDataSource)

    @Provides
    @Singleton
    fun providesOurRulesRepository(ourRulesDataSource: OurRulesDataSource): OurRulesRepository =
        OurRulesRepositoryImpl(ourRulesDataSource)

    @Provides
    @Singleton
    fun providesTodoRepository(impl: TodoRepositoryImpl): TodoRepository = impl
}

package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.datasource.HousDataSource
import hous.release.data.datasource.OurRulesDataSource
import hous.release.data.repository.AuthRepositoryImpl
import hous.release.data.repository.EnterRoomRepositoryImpl
import hous.release.data.repository.HousRepositoryImpl
import hous.release.data.repository.OurRulesRepositoryImpl
import hous.release.data.repository.TodoRepositoryImpl
import hous.release.domain.repository.AuthRepository
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.repository.HousRepository
import hous.release.domain.repository.OurRulesRepository
import hous.release.domain.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(authDataSource)

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

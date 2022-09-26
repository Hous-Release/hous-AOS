package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.repository.AuthRepositoryImpl
import hous.release.data.datasource.OurRulesDataSource
import hous.release.data.repository.EnterRoomRepositoryImpl
import hous.release.data.repository.OurRulesRepositoryImpl
import hous.release.data.repository.ToDoRepositoryImpl
import hous.release.domain.repository.AuthRepository
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.repository.OurRulesRepository
import hous.release.domain.repository.ToDoRepository
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
    fun providesOurRulesRepository(ourRulesDataSource: OurRulesDataSource): OurRulesRepository =
        OurRulesRepositoryImpl(ourRulesDataSource)

    @Provides
    @Singleton
    fun providesToDoRepository(impl: ToDoRepositoryImpl): ToDoRepository = impl
}

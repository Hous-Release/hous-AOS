package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.datasource.OurRulesDataSource
import hous.release.data.repository.EnterRoomRepositoryImpl
import hous.release.data.repository.OurRulesRepositoryImpl
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.repository.OurRulesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providesEnterRoomRepository(enterRoomDataSource: EnterRoomDataSource): EnterRoomRepository =
        EnterRoomRepositoryImpl(enterRoomDataSource)

    @Provides
    @Singleton
    fun providesOurRulesRepository(ourRulesDataSource: OurRulesDataSource): OurRulesRepository =
        OurRulesRepositoryImpl(ourRulesDataSource)
}

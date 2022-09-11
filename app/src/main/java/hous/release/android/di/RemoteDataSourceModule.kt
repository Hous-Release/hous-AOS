package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.service.EnterRoomService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Provides
    @Singleton
    fun providesEnterRoomDatasource(enterRoomService: EnterRoomService): EnterRoomDataSource =
        EnterRoomDataSource(enterRoomService)
}

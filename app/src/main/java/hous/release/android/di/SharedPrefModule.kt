package hous.release.android.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.datasource.SharedPrefDataSource

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {

    @Provides
    fun provideSharedPref(app: Application): SharedPrefDataSource {
        return SharedPrefDataSource(app.applicationContext)
    }
}

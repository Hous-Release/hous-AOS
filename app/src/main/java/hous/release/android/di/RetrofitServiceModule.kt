package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.service.EnterRoomService
import hous.release.data.service.OurRulesService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesEnterRoomService(retrofit: Retrofit): EnterRoomService =
        retrofit.create(EnterRoomService::class.java)

    @Provides
    @Singleton
    fun providesOurRulesService(retrofit: Retrofit): OurRulesService =
        retrofit.create(OurRulesService::class.java)
}

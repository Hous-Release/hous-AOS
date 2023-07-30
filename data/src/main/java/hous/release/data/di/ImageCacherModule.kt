package hous.release.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.data.util.image.ImageCacher
import hous.release.data.util.image.LocalImageCacher
import hous.release.data.util.image.RemoteImageCacher
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ImageCacherModule {

    @Provides
    @LocalCache
    fun provideLocalImageCacher(localImageCacher: LocalImageCacher): ImageCacher = localImageCacher

    @Provides
    @RemoteCache
    fun provideRemoteImageCacher(remoteImageCacher: RemoteImageCacher): ImageCacher = remoteImageCacher
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LocalCache

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RemoteCache

package hous.release.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hous.release.data.di.IoDispatcher
import hous.release.data.di.LocalCache
import hous.release.data.di.RemoteCache
import hous.release.data.util.image.FileNameFormatter
import hous.release.data.util.image.ImageCacher
import hous.release.domain.entity.Photo
import hous.release.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    @RemoteCache private val remoteCacher: ImageCacher,
    @LocalCache private val localCacher: ImageCacher,
    @IoDispatcher private val ioDispatchers: CoroutineDispatcher,
    private val fileNameFormatter: FileNameFormatter
) : PhotoRepository {

    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also { if (it.exists().not()) it.mkdir() }
    }

    private val tmpFolder by lazy {
        File(context.cacheDir, "tmp_photos").also { if (it.exists().not()) it.mkdir() }
    }

    private fun isCached(name: String) = (
        cacheFolder.listFiles()?.any {
            it.name == name
        } ?: false
        ) || (
        tmpFolder.listFiles()?.any
        {
            it.name == name
        } ?: false
        )

    // path에 해당하는 remote 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override fun fetchRemotePhotosFlow(photos: List<Photo>): Flow<List<Photo?>> = flow {
        val _photos = MutableList<Photo?>(photos.size) { null }
        emit(_photos)
        photos.forEachIndexed { index, photo ->
            val photoPath = photo.path
            val fileName = photoPath.toFileName()
            if (isCached(fileName)) { // 만약 이미 캐시된 사진이라면
                _photos[index] = Photo.from(File(cacheFolder, fileName).absolutePath)
                emit(_photos)
            } else { // 캐시된 사진이 아니라면
                val remotePhoto = remoteCacher.cacheImage(photoPath)
                remotePhoto?.let {
                    _photos[index] = Photo.from(it.absolutePath)
                    emit(_photos)
                }
            }
        }
        emit(_photos) // 마지막으로 캐시된 사진들을 emit한다.
    }.flowOn(ioDispatchers)

    // path에 해당하는 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override suspend fun fetchPhotosBy(photos: List<Photo>): List<File> =
        withContext(ioDispatchers) {
            photos.map { photo ->
                val fileName = photo.path.toFileName()
                if (isCached(fileName)) {
                    return@map photo.toCachedFile()
                }
                return@map photo.cache() ?: error("캐시된 사진이 없습니다.")
            }
        }

    private fun Photo.toCachedFile(): File {
        return when (this) {
            is Photo.Remote -> {
                File(cacheFolder, path.toFileName())
            }

            is Photo.Media -> {
                File(tmpFolder, path.toFileName())
            }

            is Photo.Cache -> {
                File(cacheFolder, path.toFileName())
            }
        }
    }

    private fun Photo.cache(): File? {
        return when (this) {
            is Photo.Remote -> {
                remoteCacher.cacheImage(path) ?: return null
                File(cacheFolder, path.toFileName())
            }

            is Photo.Media -> {
                localCacher.cacheImage(path) ?: return null
            }

            is Photo.Cache -> {
                File(cacheFolder, path.toFileName())
            }
        }
    }

    // path에 해당하는 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 삭제한다.
    override suspend fun removePhoto(path: String): Boolean {
        val fileName = path.toFileName()
        return withContext(ioDispatchers) {
            if (isCached(fileName)) {
                File(cacheFolder, fileName).delete() || File(tmpFolder, fileName).delete()
            } else {
                false
            }
        }
    }

    // 임시 캐시된 사진들을 삭제한다.
    override suspend fun removeTemporayPhotos() = withContext(ioDispatchers) {
        tmpFolder.listFiles()?.all { it.delete() } ?: run {
            Timber.e("임시 저장된 사진이 없습니다.")
            true
        }
    }

    private fun String.toFileName() = fileNameFormatter.formatImageName(this)
}

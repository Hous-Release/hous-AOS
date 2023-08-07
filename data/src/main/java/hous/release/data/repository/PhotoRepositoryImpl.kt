package hous.release.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hous.release.data.di.IoDispatcher
import hous.release.data.di.LocalCache
import hous.release.data.di.RemoteCache
import hous.release.data.util.image.FileNameFormatter
import hous.release.data.util.image.ImageCacher
import hous.release.domain.value.PhotoURL
import hous.release.domain.value.PhotoUri
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
    override fun fetchRemotePhotosFlow(urls: List<PhotoURL>): Flow<List<PhotoUri?>> = flow {
        val photos = MutableList<PhotoUri?>(urls.size) { null }
        emit(photos)
        urls.forEachIndexed { index, url ->
            val urlPath = url.path
            val fileName = urlPath.toFileName()
            if (isCached(fileName)) { // 만약 이미 캐시된 사진이라면
                photos[index] = PhotoUri(File(cacheFolder, fileName).absolutePath)
                emit(photos)
            } else { // 캐시된 사진이 아니라면
                val remotePhoto = remoteCacher.cacheImage(urlPath)
                remotePhoto?.let {
                    photos[index] = PhotoUri(it.absolutePath)
                    emit(photos)
                }
            }
        }
        emit(photos) // 마지막으로 캐시된 사진들을 emit한다.
    }.flowOn(ioDispatchers)

    // path에 해당하는 remote 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override suspend fun fetchPhotosByURL(urls: List<PhotoURL>): List<File> =
        withContext(ioDispatchers) {
            urls.map {
                val urlPath = it.path
                val fileName = urlPath.toFileName()
                if (isCached(fileName).not()) {
                    remoteCacher.cacheImage(urlPath) ?: error("캐시된 사진이 없습니다.")
                } else {
                    File(cacheFolder, fileName)
                }
            }
        }

    // path에 해당하는 local 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override suspend fun fetchPhotosByUri(uris: List<PhotoUri>): List<File> =
        withContext(ioDispatchers) {
            val files = uris.map {
                val uriPath = it.path
                val fileName = uriPath.toFileName()

                if (isCached(fileName).not()) {
                    localCacher.cacheImage(uriPath) ?: error("캐시된 사진이 없습니다.")
                } else {
                    Timber.e("캐시된 사진이 있습니다.")
                    File(tmpFolder, fileName)
                }
            }
            files
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
        var isDeleted = false
        tmpFolder.listFiles()?.forEach {
            isDeleted = it.delete() || isDeleted
        }
        isDeleted
    }

    private fun String.toFileName() = fileNameFormatter.formatImageName(this)
}

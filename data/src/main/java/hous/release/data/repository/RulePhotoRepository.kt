package hous.release.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hous.release.data.di.IoDispatcher
import hous.release.data.di.LocalCache
import hous.release.data.di.RemoteCache
import hous.release.data.util.image.ImageCacher
import hous.release.domain.enums.PhotoUri
import hous.release.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class RulePhotoRepository @Inject constructor(
    @ApplicationContext context: Context,
    @RemoteCache private val remoteCacher: ImageCacher,
    @LocalCache private val localCacher: ImageCacher,
    @IoDispatcher private val ioDispatchers: CoroutineDispatcher
) : PhotoRepository {

    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also { if (it.exists().not()) it.mkdir() }
    }

    private val tmpFolder by lazy {
        File(context.cacheDir, "tmp_photos").also { if (it.exists().not()) it.mkdir() }
    }

    private fun isExistPhoto(path: String) = (
        cacheFolder.listFiles()?.any {
            it.name == path
        } ?: false
        ) || (
        tmpFolder.listFiles()?.any
        {
            it.name == path
        } ?: false
        )

    // path에 해당하는 remote 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override fun fetchRemotePhotosFlow(paths: List<String>): Flow<List<PhotoUri?>> = flow {
        val cachedPhotos = MutableList<PhotoUri?>(paths.size) { null }
        emit(cachedPhotos)
        paths.forEachIndexed { index, path ->
            if (isExistPhoto(path)) { // 만약 이미 캐시된 사진이라면
                cachedPhotos[index] = PhotoUri(File(cacheFolder, path).absolutePath)
                emit(cachedPhotos)
            } else { // 캐시된 사진이 아니라면
                val remotePhoto = remoteCacher.cacheImage(path)
                remotePhoto?.let {
                    cachedPhotos[index] = PhotoUri(it.absolutePath)
                    emit(cachedPhotos)
                }
            }
        }
        emit(cachedPhotos) // 마지막으로 캐시된 사진들을 emit한다.
    }.flowOn(ioDispatchers)

    // path에 해당하는 remote 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override suspend fun fetchRemotePhotos(paths: List<String>): List<File> =
        withContext(ioDispatchers) {
            paths.map {
                if (isExistPhoto(it).not()) {
                    remoteCacher.cacheImage(it) ?: error("캐시된 사진이 없습니다.")
                } else {
                    File(cacheFolder, it)
                }
            }
        }

    // path에 해당하는 local 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 반환한다.
    override suspend fun fetchLocalPhotos(paths: List<String>): List<File> =
        withContext(ioDispatchers) {
            paths.map {
                if (isExistPhoto(it).not()) {
                    localCacher.cacheImage(it) ?: error("캐시된 사진이 없습니다.")
                } else {
                    File(tmpFolder, it)
                }
            }
        }

    // path에 해당하는 사진이 캐시되어 있는지 확인한 후, 캐시되어 있다면 삭제한다.
    override suspend fun removePhoto(path: String): Boolean {
        return withContext(ioDispatchers) {
            if (isExistPhoto(path)) {
                File(cacheFolder, path).delete() || File(tmpFolder, path).delete()
            } else {
                false
            }
        }
    }

    // 임시 캐시된 사진들을 삭제한다.
    override suspend fun removeTmpPhoto() = withContext(ioDispatchers) {
        var isDeleted = false
        tmpFolder.listFiles()?.forEach {
            isDeleted = it.delete() || isDeleted
        }
        isDeleted
    }
}

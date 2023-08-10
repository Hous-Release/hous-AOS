package hous.release.domain.repository

import hous.release.domain.value.PhotoURL
import hous.release.domain.value.PhotoUri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun fetchRemotePhotosFlow(paths: List<PhotoURL>): Flow<List<PhotoUri?>>
    suspend fun fetchPhotosByURL(urls: List<PhotoURL>): List<File>
    suspend fun fetchPhotosByUri(uris: List<PhotoUri>): List<File>
    suspend fun removePhoto(path: String): Boolean
    suspend fun removeTemporayPhotos(): Boolean
}

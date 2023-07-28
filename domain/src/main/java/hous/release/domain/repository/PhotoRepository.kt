package hous.release.domain.repository

import hous.release.domain.enums.PhotoURL
import hous.release.domain.enums.PhotoUri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun fetchRemotePhotosFlow(paths: List<String>): Flow<List<PhotoUri?>>
    suspend fun fetchPhotos(urls: List<PhotoURL>): List<File>
    suspend fun fetchPhotos(uris: List<PhotoUri>): List<File>
    suspend fun removePhoto(path: String): Boolean
    suspend fun removeTemporayPhotos(): Boolean
}

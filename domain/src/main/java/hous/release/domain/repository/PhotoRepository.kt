package hous.release.domain.repository

import hous.release.domain.enums.PhotoUri
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun fetchRemotePhotosFlow(paths: List<String>): Flow<List<PhotoUri?>>
    suspend fun fetchRemotePhotos(paths: List<String>): List<File>
    suspend fun fetchLocalPhotos(paths: List<String>): List<File>
    suspend fun removePhoto(path: String): Boolean
    suspend fun removeTmpPhoto(): Boolean
}

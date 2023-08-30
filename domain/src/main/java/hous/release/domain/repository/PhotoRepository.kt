package hous.release.domain.repository

import hous.release.domain.entity.Photo
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun fetchPhotosFlow(paths: List<Photo>): Flow<List<Photo?>>
    suspend fun fetchPhotosBy(uris: List<Photo>): List<File>
    suspend fun removePhoto(path: String): Boolean
    suspend fun removeTemporayPhotos(): Boolean
}

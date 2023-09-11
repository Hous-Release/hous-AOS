package hous.release.domain.repository

interface ImageRepository {
    suspend fun downloadImage(imageUrl: String, fileName: String)
}

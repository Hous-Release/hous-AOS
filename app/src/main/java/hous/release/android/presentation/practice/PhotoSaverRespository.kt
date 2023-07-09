package hous.release.android.presentation.practice

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class PhotoSaverRepository(
    context: Context,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) {
    private val contentResolver = context.contentResolver
    private val _photos = mutableListOf<File>()
    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also { it.mkdir() }
    }

    private fun generateFileName() = "${System.currentTimeMillis()}.jpg"
    private fun generatePhotoCacheFile() = File(cacheFolder, generateFileName())

    fun fetchPhotos() = _photos.toList()
    fun isEmpty() = _photos.isEmpty()
    fun canAddPhoto() = _photos.size < MAX_PHOTOS_LIMIT

    suspend fun cacheFromUrls(urls: List<String>) {
        withContext(dispatchers) {
            urls.forEach {
                cacheFromUrl(it)
            }
        }
    }

    suspend fun cacheFromUris(uris: List<Uri>) {
        withContext(dispatchers) {
            Timber.i("cacheFromUris: $uris")
            uris.forEach {
                cacheFromUri(it)
            }
        }
    }

    suspend fun removeFile(file: File) {
        withContext(dispatchers) {
            file.delete()
            _photos -= file
        }
    }

    suspend fun removeAllFile() {
        withContext(dispatchers) {
            _photos.forEach { it.delete() }
            _photos.clear()
        }
    }

    suspend fun savePhotos(): List<File> {
        return withContext(dispatchers) {
            val savedPhotos = _photos.toList()
            removeAllFile()
            savedPhotos
        }
    }

    private fun cacheFromUrl(src: String) {
        if (_photos.size + 1 > MAX_PHOTOS_LIMIT) {
            return
        }

        val cachedPhoto = generatePhotoCacheFile()
        val url = URL(src)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val input = connection.inputStream
        cachedPhoto.outputStream().use { output ->
            input.copyTo(output)
        }

        _photos += cachedPhoto
    }

    private fun cacheFromUri(uri: Uri) {
        if (_photos.size + 1 > MAX_PHOTOS_LIMIT) {
            return
        }

        contentResolver.openInputStream(uri)?.use { input ->
            val cachedPhoto = generatePhotoCacheFile()

            cachedPhoto.outputStream().use { output ->
                input.copyTo(output)
                _photos += cachedPhoto
            }
        }
    }

    companion object {
        private const val MAX_PHOTOS_LIMIT = 5
    }
}

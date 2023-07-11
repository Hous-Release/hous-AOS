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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class PhotoSaverRepository(
    context: Context,
    private val bitmapManager: BitmapManager,
    private val dispatchers: CoroutineDispatcher = Dispatchers.IO
) {
    private val _photos = mutableListOf<File>()

    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also { it.mkdir() }
    }

    private fun generateFileName() = FILE_NAME_FORMAT.format(
        LocalDateTime.now().format(
            DATE_FORMATTER
        ),
        EXTENSION
    )

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
            uris.forEach {
                cacheFromUri(it)
            }
        }
    }

    suspend fun removeFile(file: File): Boolean {
        return withContext(dispatchers) {
            val isRemoved = file.delete()
            _photos -= file
            isRemoved
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
        bitmapManager.cacheBitmapFromUri(uri)?.let { cachedPhoto ->
            _photos += cachedPhoto
            Timber.i("cachedPhotoPath: ${cachedPhoto.absolutePath}")
        }
    }

    companion object {
        private const val MAX_PHOTOS_LIMIT = 5
        private const val DATE_TIME_Pattern = "yyyyMMdd_HHmmss"
        private const val TIME_ZONE = "Asia/Seoul"
        private var DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_Pattern)
            .withZone(TimeZone.getTimeZone(TIME_ZONE).toZoneId())
        private const val EXTENSION = "jpg"
        private const val FILE_NAME_FORMAT = "%s.%s"
    }
}

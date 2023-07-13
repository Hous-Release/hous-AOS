package hous.release.android.presentation.practice

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import hous.release.android.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import javax.inject.Inject

class PhotoSaverRepository
@Inject constructor(
    @ApplicationContext context: Context,
    private val bitmapManager: BitmapManager,
    @IoDispatcher private val ioDispatchers: CoroutineDispatcher
) {
    private val _photos = mutableListOf<File>()

    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also { if (it.exists().not()) it.mkdir() }
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
        withContext(ioDispatchers) {
            urls.forEach {
                cacheFromUrl(it)
            }
        }
    }

    suspend fun cacheFromUris(uris: List<Uri>) {
        withContext(ioDispatchers) {
            uris.forEach {
                cacheFromUri(it)
            }
        }
    }

    suspend fun removeFile(file: File): Boolean {
        return withContext(ioDispatchers) {
            val isRemoved = file.delete()
            Timber.e("isRemoved: $isRemoved")
            _photos -= file
            isRemoved
        }
    }

    suspend fun removeAllFile() {
        withContext(ioDispatchers) {
            _photos.clear()
            cacheFolder.listFiles()?.forEach {
                it.delete()
            }
        }
        Timber.e("removeAllFile")
    }

    suspend fun savePhotos(): List<File> {
        return withContext(ioDispatchers) {
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

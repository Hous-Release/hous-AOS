package hous.release.android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException

class BitmapDecoder(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val contentResolver by lazy { context.contentResolver }

    suspend fun decode(path: String): Bitmap? = withContext(ioDispatcher) {
        runCatching {
            // cache에 저장된 이미지 decode
            val inputStream = contentResolver.openInputStream(
                File(path).toUri()
            )
            inputStream?.use { _inputStream ->
                BitmapFactory.decodeStream(_inputStream)
            }
        }.recover {
            // Gallery에서 선택한 이미지
            if (it is FileNotFoundException) {
                val inputStream = contentResolver.openInputStream(
                    path.toUri()
                )
                return@recover inputStream
                    ?.use { _inputStream ->
                        BitmapFactory.decodeStream(_inputStream)
                    }?.rotateBitMap(getOrientationOfImage(path.toUri()))
            }
            null
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull()
    }

    private fun Bitmap.rotateBitMap(orientation: Int): Bitmap {
        return runCatching {
            val matrix = Matrix().apply {
                postRotate(orientation.toFloat())
            }
            val rotatedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
            if (isRecycled.not() && rotatedBitmap != this) recycle()
            rotatedBitmap
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull() ?: this
    }

    private fun getOrientationOfImage(uri: Uri): Int {
        return runCatching {
            context.contentResolver.openInputStream(uri)?.use { input ->
                val orientation = ExifInterface(input).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }
            }
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull() ?: ExifInterface.ORIENTATION_NORMAL
    }
}

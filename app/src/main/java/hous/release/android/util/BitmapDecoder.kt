package hous.release.android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
                return@recover inputStream?.use { _inputStream ->
                    BitmapFactory.decodeStream(_inputStream)
                }
            }
            null
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull()
    }
}

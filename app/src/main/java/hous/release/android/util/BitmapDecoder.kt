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

class BitmapDecoder(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val contentResolver by lazy { context.contentResolver }

    suspend fun decode(path: String): Bitmap? = withContext(ioDispatcher) {
        runCatching {
            val inputStream = contentResolver.openInputStream(
                File(path).toUri()
            )
            inputStream?.use { _inputStream ->
                BitmapFactory.decodeStream(_inputStream)
            }
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull()
    }
}

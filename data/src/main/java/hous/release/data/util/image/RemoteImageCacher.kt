package hous.release.data.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class RemoteImageCacher @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileNameFormatter: FileNameFormatter
) : ImageCacher {

    private val cacheFolder by lazy {
        File(context.cacheDir, "photos").also {
            if (it.exists().not()) it.mkdir()
        }
    }

    private fun generatePhotoCacheFile(name: String) =
        File(cacheFolder, fileNameFormatter.formatImageName(name))

    override fun cacheImage(url: String): File? = runCatching {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.run {
            doInput = true
            connect()
            inputStream.use { input ->
                BitmapFactory.decodeStream(input)
            }?.run {
                val cachedFile = generatePhotoCacheFile(url).apply { createNewFile() }
                cachedFile.outputStream().use { output ->
                    compress(Bitmap.CompressFormat.JPEG, 100, output)
                    if (isRecycled.not()) recycle()
                }
                cachedFile
            }
        }
    }.onFailure {
        Timber.e(it.stackTraceToString())
    }.getOrNull()
}

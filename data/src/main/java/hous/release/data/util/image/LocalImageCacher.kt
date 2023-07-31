package hous.release.data.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.core.graphics.scale
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class LocalImageCacher @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fileNameFormatter: FileNameFormatter
) : ImageCacher {

    private val tmpFolder by lazy {
        File(context.cacheDir, "tmp_photos").also {
            if (it.exists().not()) it.mkdir()
        }
    }

    private fun generatePhotoCacheFile(name: String) =
        File(tmpFolder, fileNameFormatter.formatImageName(name))

    override fun cacheImage(path: String): File? {
        val uri = path.toUri()
        return runCatching {
            val options = BitmapFactory.Options()
            context.contentResolver.openInputStream(uri).use { input ->
                BitmapFactory.decodeStream(input, null, options)?.resizeBitmap(
                    MAX_SIZE,
                    MAX_SIZE
                )
            }?.rotateBitMap(
                getOrientationOfImage(uri)
            )?.run {
                val cachedFile = generatePhotoCacheFile(path).apply { createNewFile() }
                cachedFile.outputStream().use { output ->
                    compress(Bitmap.CompressFormat.JPEG, 100, output)
                    if (isRecycled.not()) recycle()
                }
                Timber.e(
                    "cacheImage() : ${cachedFile.exists()}"
                )
                cachedFile
            }
        }.onFailure {
            Timber.e("cacheImage() :" + it.stackTraceToString())
        }.getOrNull()
    }

    /***
     * bitmap을 required_width, required_height에 맞게 resize하여 반환한다.
     */
    private fun Bitmap.resizeBitmap(
        requiredWidth: Int,
        requiredHeight: Int
    ): Bitmap {
        return runCatching {
            val width = width
            val height = height
            if (width <= requiredWidth && height <= requiredHeight) return this
            val resizedWidth: Int
            val resizedHeight: Int
            if (width >= height) {
                resizedWidth = requiredWidth
                resizedHeight = (requiredWidth * (height.toFloat() / width.toFloat())).toInt()
                val resizedBitmap = scale(resizedWidth, resizedHeight)
                if (isRecycled.not()) recycle()
                return resizedBitmap
            }
            resizedWidth = (requiredHeight * (width.toFloat() / height.toFloat())).toInt()
            resizedHeight = requiredHeight
            val resizedBitmap = scale(resizedWidth, resizedHeight)
            if (isRecycled.not()) recycle()
            return resizedBitmap
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull() ?: this
    }

    /***
     * 이미지의 회전값을 받아서 이미지를 회전시킨다.
     *
     * Matrix : 이미지를 회전시키기 위한 클래스(2D 변환(회전, 확대/축소, 이동 등)을 수행하기 위해 사용된다)
     * postRotate : 이미지를 회전할 각도 지정
     * createBitmap : 회전된 이미지를 반환하는 메서드
     */
    private fun Bitmap.rotateBitMap(orientation: Int): Bitmap {
        return runCatching {
            val matrix = Matrix().apply {
                postRotate(orientation.toFloat())
            }
            val rotatedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
            if (isRecycled.not()) recycle()
            rotatedBitmap
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull() ?: this
    }

    /***
     * 이미지 Uri를 받아서 이미지의 회전값을 반환한다.
     *
     * ExifInterface : 이미지의 정보를 가지고 있는 클래스
     * TAG_ORIENTATION : 이미지의 회전값을 나타내는 상수
     * ORIENTATION_NORMAL : 회전값이 없는 상태
     */
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

    private companion object {
        private const val MAX_SIZE = 720
    }
}

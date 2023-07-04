package hous.release.android.presentation.practice

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.core.graphics.scale
import dagger.hilt.android.qualifiers.ActivityContext
import hous.release.android.R
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat

class BitmapManager(
    @ActivityContext private val context: Context
) {
    /***
     * uri에 해당하는 이미지의 bitmap을 resize하여 반환한다.
     */
    fun optimizeBitmapFromUri(
        uri: Uri,
        requiredWidth: Int = MAX_SIZE,
        requiredHeight: Int = MAX_SIZE
    ): Bitmap {
        return runCatching {
            val options = BitmapFactory.Options()
            Timber.d("uri : $uri")
            context.contentResolver.openInputStream(uri)
                .use { input ->
                    BitmapFactory.decodeStream(input, null, options)
                        ?.resizeBitmap(
                            requiredWidth,
                            requiredHeight
                        )
                }?.rotateBitMap(
                    getOrientationOfImage(uri)
                )
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull() ?: BitmapFactory.decodeResource(
            context.resources,
            R.drawable.ic_alarm
        )
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
                return scale(resizedWidth, resizedHeight)
            }
            resizedWidth = (requiredHeight * (width.toFloat() / height.toFloat())).toInt()
            resizedHeight = requiredHeight
            scale(resizedWidth, resizedHeight)
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
    private fun Bitmap.rotateBitMap(orientation: Int): Bitmap? {
        return runCatching {
            val matrix = Matrix().apply {
                postRotate(orientation.toFloat())
            }
            Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
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
                Timber.d("orientation : $orientation")
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

    /***
     * Bitmap을 JPEG로 변환하여 캐시 디렉토리에 저장한후, 파일의 절대 경로를 반환한다.
     * TODO : 쓸모 없으면 추후 삭제 예정
     */
    @SuppressLint("SimpleDateFormat")
    fun cacheBitmap(
        bitmap: Bitmap,
        extension: String = "jpg"
    ): String? {
        return runCatching {
            val cacheDir: File = context.cacheDir
            val fileName = FILE_NAME_FORMAT.format(
                SimpleDateFormat(DATE_FORAMT).format(System.currentTimeMillis()),
                extension
            )
            val tempFile = File(cacheDir, fileName).apply { createNewFile() }
            tempFile.outputStream().use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                if (bitmap.isRecycled.not()) bitmap.recycle()
            }
            tempFile.absolutePath
        }.onFailure {
            Timber.e(it.stackTraceToString())
        }.getOrNull()
    }

    companion object {
        private const val DATE_FORAMT = "yyyyMMdd_HHmmss"
        private const val FILE_NAME_FORMAT = "%s.%s"
        private const val MAX_SIZE = 720
    }
}

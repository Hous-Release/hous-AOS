package hous.release.data.util.image

import java.io.File

fun interface ImageCacher {
    fun cacheImage(url: String): File?
}

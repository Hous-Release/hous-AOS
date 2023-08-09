package hous.release.data.util.image

import javax.inject.Inject

class FileNameFormatter @Inject constructor() {
    fun formatImageName(path: String): String {
        return path.substringAfterLast("/").substringBefore(".") + EXTENSION
    }

    private companion object {
        const val EXTENSION = ".jpg"
    }
}

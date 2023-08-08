package hous.release.domain.entity

sealed class Photo {
    abstract val path: String

    data class Remote internal constructor(override val path: String) : Photo()
    data class Cache internal constructor(override val path: String) : Photo()
    data class Media internal constructor(override val path: String) : Photo()

    companion object {
        fun from(path: String): Photo {
            return when {
                path.startsWith(HOUS_S3_BASE_URL) -> Remote(path)
                path.startsWith(CONTENT_SCHEME) -> Media(path)
                path.contains(CACHE_DIR) -> Cache(path)
                else -> {
                    throw IllegalArgumentException("path : [$path] must start with https:// or content:// or hous.release.android/cache")
                }
            }
        }

        private const val HOUS_S3_BASE_URL = "https://team-hous.s3.ap-northeast-2.amazonaws.com/"
        private const val CACHE_DIR = "hous.release.android/cache"
        private const val CONTENT_SCHEME = "content://"
    }
}

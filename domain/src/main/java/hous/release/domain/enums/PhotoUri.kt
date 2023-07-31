package hous.release.domain.enums

@JvmInline
value class PhotoUri(val path: String) {
    init {
        require(path.isNotBlank() && path.substringBefore(":") == SCHEME)
    }

    private companion object {
        const val SCHEME = "content"
    }
}

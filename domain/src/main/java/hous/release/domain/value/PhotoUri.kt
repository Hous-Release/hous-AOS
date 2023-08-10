package hous.release.domain.value

@JvmInline
value class PhotoUri(val path: String) {
    init {
        require(path.isNotBlank())
    }
}

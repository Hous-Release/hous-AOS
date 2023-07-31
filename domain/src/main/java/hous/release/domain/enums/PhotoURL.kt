package hous.release.domain.enums

@JvmInline
value class PhotoURL(val path: String) {
    init {
        require(path.isNotBlank())
    }
}
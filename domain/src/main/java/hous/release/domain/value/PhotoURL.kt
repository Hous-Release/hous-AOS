package hous.release.domain.value

@JvmInline
value class PhotoURL(val path: String) {
    init {
        require(path.startsWith(HOUS_S3_BASE_URL)) { "path must start with $HOUS_S3_BASE_URL" }
    }

    private companion object {
        const val HOUS_S3_BASE_URL = "https://team-hous.s3.ap-northeast-2.amazonaws.com/"
    }
}

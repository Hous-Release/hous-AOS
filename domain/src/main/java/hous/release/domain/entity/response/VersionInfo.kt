package hous.release.domain.entity.response

data class VersionInfo(
    val marketUrl: String = "",
    val needsForceUpdate: Boolean = false
)

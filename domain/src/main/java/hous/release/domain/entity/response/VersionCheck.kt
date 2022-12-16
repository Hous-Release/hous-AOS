package hous.release.domain.entity.response

data class VersionCheck(
    val marketUrl: String = "",
    val needsForceUpdate: Boolean = false
)

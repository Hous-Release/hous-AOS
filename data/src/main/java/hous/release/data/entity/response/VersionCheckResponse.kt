package hous.release.data.entity.response

import hous.release.domain.entity.response.VersionInfo

data class VersionCheckResponse(
    val marketUrl: String,
    val needsForceUpdate: Boolean
) {
    fun toVersionInfo(): VersionInfo =
        VersionInfo(
            marketUrl = this.marketUrl,
            needsForceUpdate = this.needsForceUpdate
        )
}

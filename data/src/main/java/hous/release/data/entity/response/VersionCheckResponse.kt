package hous.release.data.entity.response

import hous.release.domain.entity.response.VersionCheck

data class VersionCheckResponse(
    val marketUrl: String,
    val needsForceUpdate: Boolean
) {
    fun toVersionCheck(): VersionCheck =
        VersionCheck(
            marketUrl = this.marketUrl,
            needsForceUpdate = this.needsForceUpdate
        )
}

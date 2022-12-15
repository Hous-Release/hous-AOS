package hous.release.data.datasource

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.VersionCheckResponse
import hous.release.data.service.VersionService
import javax.inject.Inject

class VersionDataSource @Inject constructor(
    private val versionService: VersionService
) {
    suspend fun getVersionCheck(): BaseResponse<VersionCheckResponse> =
        versionService.getVersionCheck()
}

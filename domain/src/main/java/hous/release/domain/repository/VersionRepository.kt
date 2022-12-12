package hous.release.domain.repository

import hous.release.domain.entity.response.VersionInfo

interface VersionRepository {
    suspend fun getVersionCheck(): Result<VersionInfo>
}

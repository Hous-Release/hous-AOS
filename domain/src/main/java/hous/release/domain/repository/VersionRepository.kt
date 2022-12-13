package hous.release.domain.repository

import hous.release.domain.entity.response.VersionCheck

interface VersionRepository {
    suspend fun getVersionCheck(): Result<VersionCheck>
}

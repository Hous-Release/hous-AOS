package hous.release.data.repository

import hous.release.data.datasource.VersionDataSource
import hous.release.domain.entity.response.VersionInfo
import hous.release.domain.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val versionDataSource: VersionDataSource
) : VersionRepository {
    override suspend fun getVersionCheck(): Result<VersionInfo> =
        kotlin.runCatching {
            versionDataSource.getVersionCheck()
        }.map { response -> response.data.toVersionInfo() }
}

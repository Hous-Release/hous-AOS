package hous.release.data.repository

import hous.release.data.datasource.VersionDataSource
import hous.release.domain.entity.response.VersionCheck
import hous.release.domain.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val versionDataSource: VersionDataSource
) : VersionRepository {
    override suspend fun getVersionCheck(): Result<VersionCheck> =
        kotlin.runCatching {
            versionDataSource.getVersionCheck()
        }.map { response -> response.data.toVersionCheck() }
}

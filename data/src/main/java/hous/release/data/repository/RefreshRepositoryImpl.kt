package hous.release.data.repository

import hous.release.data.datasource.LocalPrefTokenDataSource
import hous.release.data.datasource.RefreshDataSource
import hous.release.data.entity.TokenEntity
import hous.release.domain.entity.response.RefreshHousToken
import hous.release.domain.repository.RefreshRepository
import javax.inject.Inject

class RefreshRepositoryImpl @Inject constructor(
    private val localPrefTokenDataSource: LocalPrefTokenDataSource,
    private val refreshDataSource: RefreshDataSource
) : RefreshRepository {
    override suspend fun refreshHousToken(): Result<RefreshHousToken> =
        kotlin.runCatching {
            refreshDataSource.refreshHousToken(
                TokenEntity(
                    accessToken = localPrefTokenDataSource.accessToken,
                    refreshToken = localPrefTokenDataSource.refreshToken
                )
            )
        }.map { response -> response.data.toRefreshHousToken() }

    companion object {
        const val EXPIRED_TOKEN = 401
    }
}

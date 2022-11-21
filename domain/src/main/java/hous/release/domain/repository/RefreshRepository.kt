package hous.release.domain.repository

import hous.release.domain.entity.response.RefreshHousToken

interface RefreshRepository {
    suspend fun refreshHousToken(): Result<RefreshHousToken>
}

package hous.release.data.datasource

import hous.release.data.entity.TokenEntity
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.RefreshHousTokenResponse
import hous.release.data.service.AuthService
import javax.inject.Inject

class RefreshDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun refreshHousToken(body: TokenEntity): BaseResponse<RefreshHousTokenResponse> =
        authService.refreshHousToken(body)
}

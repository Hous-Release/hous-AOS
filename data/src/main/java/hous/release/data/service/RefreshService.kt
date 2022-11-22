package hous.release.data.service

import hous.release.data.entity.TokenEntity
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.RefreshHousTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshService {
    @POST("/v1/auth/refresh")
    suspend fun refreshHousToken(
        @Body body: TokenEntity
    ): BaseResponse<RefreshHousTokenResponse>
}

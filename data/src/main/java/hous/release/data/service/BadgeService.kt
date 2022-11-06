package hous.release.data.service

import hous.release.data.entity.response.BadgeResponse
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface BadgeService {
    @GET("/v1/user/badges")
    suspend fun getBadges(): BaseResponse<BadgeResponse>

    @PUT("/v1/user/badge/{badgeId}/represent")
    suspend fun changeRepresentBadge(@Path("badgeId") badgeId: Int): NoDataResponse
}

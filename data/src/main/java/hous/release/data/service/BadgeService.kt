package hous.release.data.service

import hous.release.data.entity.response.BadgeResponse
import hous.release.data.entity.response.BaseResponse
import retrofit2.http.GET

interface BadgeService {
    @GET("v1/user/badges")
    suspend fun getBadges(): BaseResponse<BadgeResponse>
}

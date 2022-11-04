package hous.release.data.service

import hous.release.data.entity.request.NotificationSettingsRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.NotificationSettingsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface SettingsService {
    @GET("/v1/user/push")
    suspend fun getNotificationSettings(): BaseResponse<NotificationSettingsResponse>

    @PATCH("/v1/user/push")
    suspend fun patchNotificationSettings(
        @Body body: NotificationSettingsRequest
    ): NoDataResponse
}

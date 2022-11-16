package hous.release.data.service

import hous.release.data.entity.request.NotificationSettingsRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.NotificationSettingsResponse
import hous.release.data.entity.response.SettingsMyToDoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface SettingsService {
    @GET("/v1/user/push")
    suspend fun getNotificationSettings(): BaseResponse<NotificationSettingsResponse>

    @PATCH("/v1/user/push")
    suspend fun patchNotificationSettings(
        @Body body: NotificationSettingsRequest
    ): NoDataResponse

    @GET("/v1/todos/me")
    suspend fun getSettingsMyToDo(): BaseResponse<SettingsMyToDoResponse>

    @DELETE("/v1/room/leave")
    suspend fun deleteRoom(): NoDataResponse
}

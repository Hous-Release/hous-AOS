package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {
    @GET("/v1/user/notifications")
    suspend fun getNotification(
        @Query("lastNotificationId") lastNotificationId: Int,
        @Query("size") size: Int
    ): BaseResponse<NotificationResponse>
}

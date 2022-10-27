package hous.release.data.datasource

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NotificationResponse
import hous.release.data.service.NotificationService
import javax.inject.Inject

class NotificationDataSource @Inject constructor(
    private val notificationService: NotificationService
) {
    suspend fun getNotification(
        lastNotificationId: Int,
        size: Int
    ): BaseResponse<NotificationResponse> =
        notificationService.getNotification(lastNotificationId, size)
}

package hous.release.domain.repository

import hous.release.domain.entity.response.Notification

interface NotificationRepository {
    suspend fun getNotification(lastNotificationId: Long): Result<Notification>
}

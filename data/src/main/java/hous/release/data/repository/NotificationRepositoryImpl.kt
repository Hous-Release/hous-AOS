package hous.release.data.repository

import hous.release.data.datasource.NotificationDataSource
import hous.release.domain.entity.response.Notification
import hous.release.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun getNotification(lastNotificationId: Long): Result<Notification> =
        kotlin.runCatching {
            notificationDataSource.getNotification(lastNotificationId)
        }.map { response ->
            response.data.toNotification()
        }
}

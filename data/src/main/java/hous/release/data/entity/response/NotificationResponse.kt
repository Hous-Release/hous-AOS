package hous.release.data.entity.response

import hous.release.domain.entity.NotificationType
import hous.release.domain.entity.response.Notification
import hous.release.domain.entity.response.NotificationContent

data class NotificationResponse(
    val contents: List<NotificationContentEntity>,
    val nextCursor: Int,
    val totalElements: Int
) {
    fun toNotification(): Notification = Notification(
        contents = this.contents.map { notification ->
            NotificationContent(
                title = NotificationType.valueOf(notification.type).type,
                content = notification.content,
                createdAt = notification.createdAt,
                isRead = notification.isRead,
                notificationId = notification.notificationId,
                type = notification.type
            )
        },
        nextCursor = this.nextCursor,
        totalElements = this.totalElements
    )
}

data class NotificationContentEntity(
    val content: String,
    val createdAt: String,
    val isRead: Boolean,
    val notificationId: Int,
    val type: String
)

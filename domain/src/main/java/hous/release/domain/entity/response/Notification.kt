package hous.release.domain.entity.response

data class Notification(
    val contents: List<NotificationContent>,
    val nextCursor: Int,
    val totalElements: Int
)

data class NotificationContent(
    val title: String,
    val content: String,
    val createdAt: String,
    val isRead: Boolean,
    val notificationId: Int,
    val type: String
)

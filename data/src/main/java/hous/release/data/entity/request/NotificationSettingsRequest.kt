package hous.release.data.entity.request

data class NotificationSettingsRequest(
    val isPushNotification: Boolean?,
    val rulesPushStatus: String?,
    val newTodoPushStatus: String?,
    val todayTodoPushStatus: String?,
    val remindTodoPushStatus: String?,
    val badgePushStatus: String?
)

package hous.release.domain.entity.response

data class NotificationSettings(
    val isPushNotification: Boolean,
    val rulesPushStatus: String,
    val newTodoPushStatus: String,
    val todayTodoPushStatus: String,
    val remindTodoPushStatus: String,
    val badgePushStatus: String
)

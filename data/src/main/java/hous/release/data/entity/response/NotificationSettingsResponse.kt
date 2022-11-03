package hous.release.data.entity.response

import hous.release.domain.entity.NotificationStatusType
import hous.release.domain.entity.response.NotificationSettings

data class NotificationSettingsResponse(
    val isPushNotification: Boolean,
    val rulesPushStatus: String,
    val newTodoPushStatus: String,
    val todayTodoPushStatus: String,
    val remindTodoPushStatus: String,
    val badgePushStatus: String
) {
    fun toNotificationSettings(): NotificationSettings =
        NotificationSettings(
            isPushNotification = this.isPushNotification,
            rulesPushStatus = NotificationStatusType.valueOf(this.rulesPushStatus),
            newTodoPushStatus = NotificationStatusType.valueOf(this.newTodoPushStatus),
            todayTodoPushStatus = NotificationStatusType.valueOf(this.todayTodoPushStatus),
            remindTodoPushStatus = NotificationStatusType.valueOf(this.remindTodoPushStatus),
            badgePushStatus = NotificationStatusType.valueOf(this.badgePushStatus)
        )
}

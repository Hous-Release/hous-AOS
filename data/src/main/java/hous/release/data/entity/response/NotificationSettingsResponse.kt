package hous.release.data.entity.response

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
            rulesPushStatus = this.rulesPushStatus,
            newTodoPushStatus = this.newTodoPushStatus,
            todayTodoPushStatus = this.todayTodoPushStatus,
            remindTodoPushStatus = this.remindTodoPushStatus,
            badgePushStatus = this.badgePushStatus
        )
}

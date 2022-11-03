package hous.release.domain.entity.response

import hous.release.domain.entity.NotificationStatusType

data class NotificationSettings(
    val isPushNotification: Boolean = true,
    val rulesPushStatus: NotificationStatusType = NotificationStatusType.ON,
    val newTodoPushStatus: NotificationStatusType = NotificationStatusType.ON_ALL,
    val todayTodoPushStatus: NotificationStatusType = NotificationStatusType.ON_ALL,
    val remindTodoPushStatus: NotificationStatusType = NotificationStatusType.ON_ALL,
    val badgePushStatus: NotificationStatusType = NotificationStatusType.ON
)

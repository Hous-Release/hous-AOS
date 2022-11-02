package hous.release.data.datasource

import hous.release.data.entity.request.NotificationSettingsRequest
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.service.SettingsService
import javax.inject.Inject

class SettingsDataSource @Inject constructor(
    private val settingsService: SettingsService
) {
    suspend fun patchNotificationSettingsService(
        notificationStatus: Boolean?,
        newRulesStatus: String?,
        newTodoStatus: String?,
        startTodoStatus: String?,
        remindTodoStatus: String?,
        badgeStatus: String?
    ): NoDataResponse =
        settingsService.patchNotificationSettings(
            NotificationSettingsRequest(
                isPushNotification = notificationStatus,
                rulesPushStatus = newRulesStatus,
                newTodoPushStatus = newTodoStatus,
                todayTodoPushStatus = startTodoStatus,
                remindTodoPushStatus = remindTodoStatus,
                badgePushStatus = badgeStatus
            )
        )
}

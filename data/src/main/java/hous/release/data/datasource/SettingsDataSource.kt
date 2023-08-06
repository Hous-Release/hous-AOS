package hous.release.data.datasource

import hous.release.data.entity.request.FeedbackRequest
import hous.release.data.entity.request.NotificationSettingsRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.NotificationSettingsResponse
import hous.release.data.entity.response.SettingsMyToDoResponse
import hous.release.data.service.SettingsService
import javax.inject.Inject

class SettingsDataSource @Inject constructor(
    private val settingsService: SettingsService
) {
    suspend fun getNotificationSettings(): BaseResponse<NotificationSettingsResponse> =
        settingsService.getNotificationSettings()

    suspend fun patchNotificationSettings(
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

    suspend fun getSettingsMyToDo(): BaseResponse<SettingsMyToDoResponse> =
        settingsService.getSettingsMyToDo()

    suspend fun deleteRoom(): NoDataResponse =
        settingsService.deleteRoom()

    suspend fun postWithdrawFeedback(comment: String): NoDataResponse =
        settingsService.postWithdrawFeedback(FeedbackRequest(comment = comment))
}

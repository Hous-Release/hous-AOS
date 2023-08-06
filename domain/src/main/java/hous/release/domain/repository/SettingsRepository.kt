package hous.release.domain.repository

import hous.release.domain.entity.response.NotificationSettings
import hous.release.domain.entity.response.SettingsMyToDo

interface SettingsRepository {
    suspend fun getNotificationSettings(): Result<NotificationSettings>

    suspend fun patchNotificationSettings(
        notificationStatus: Boolean? = null,
        newRulesStatus: String? = null,
        newTodoStatus: String? = null,
        startTodoStatus: String? = null,
        remindTodoStatus: String? = null,
        badgeStatus: String? = null
    ): Result<Boolean>

    suspend fun getSettingsMyToDo(): Result<SettingsMyToDo>

    suspend fun deleteRoom(): Result<Boolean>

    suspend fun postWithdrawFeedback(comment: String): Result<Unit>
}

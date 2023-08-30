package hous.release.data.repository

import hous.release.data.datasource.SettingsDataSource
import hous.release.domain.entity.response.NotificationSettings
import hous.release.domain.entity.response.SettingsMyToDo
import hous.release.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {
    override suspend fun getNotificationSettings(): Result<NotificationSettings> =
        kotlin.runCatching {
            settingsDataSource.getNotificationSettings()
        }.map { response -> response.data.toNotificationSettings() }

    override suspend fun patchNotificationSettings(
        notificationStatus: Boolean?,
        newRulesStatus: String?,
        newTodoStatus: String?,
        startTodoStatus: String?,
        remindTodoStatus: String?,
        badgeStatus: String?
    ): Result<Boolean> =
        kotlin.runCatching {
            settingsDataSource.patchNotificationSettings(
                notificationStatus = notificationStatus,
                newRulesStatus = newRulesStatus,
                newTodoStatus = newTodoStatus,
                startTodoStatus = startTodoStatus,
                remindTodoStatus = remindTodoStatus,
                badgeStatus = badgeStatus
            )
        }.map { response -> response.success }

    override suspend fun getSettingsMyToDo(): Result<SettingsMyToDo> =
        kotlin.runCatching { settingsDataSource.getSettingsMyToDo() }
            .map { response -> response.data.toSettingsMyToDo() }

    override suspend fun deleteRoom(): Result<Boolean> =
        kotlin.runCatching { settingsDataSource.deleteRoom() }
            .map { response -> response.success }

    override suspend fun postFeedback(
        comment: String,
        isDeleting: Boolean
    ): Result<Unit> = kotlin.runCatching {
        settingsDataSource.postFeedback(
            comment = comment,
            isDeleting = isDeleting
        )
    }
}

package hous.release.data.repository

import hous.release.data.datasource.SettingsDataSource
import hous.release.domain.entity.response.NotificationSettings
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
    ): Result<Boolean> = kotlin.runCatching {
        settingsDataSource.patchNotificationSettings(
            notificationStatus = notificationStatus,
            newRulesStatus = newRulesStatus,
            newTodoStatus = newTodoStatus,
            startTodoStatus = startTodoStatus,
            remindTodoStatus = remindTodoStatus,
            badgeStatus = badgeStatus
        )
    }.map { response -> response.success }
}
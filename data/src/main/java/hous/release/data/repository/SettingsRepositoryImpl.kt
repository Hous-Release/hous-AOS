package hous.release.data.repository

import hous.release.data.datasource.SettingsDataSource
import hous.release.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {
    override suspend fun patchNotificationSettings(
        notificationStatus: Boolean?,
        newRulesStatus: String?,
        newTodoStatus: String?,
        startTodoStatus: String?,
        remindTodoStatus: String?,
        badgeStatus: String?
    ) = kotlin.runCatching {
        settingsDataSource.patchNotificationSettingsService(
            notificationStatus = notificationStatus,
            newRulesStatus = newRulesStatus,
            newTodoStatus = newTodoStatus,
            startTodoStatus = startTodoStatus,
            remindTodoStatus = remindTodoStatus,
            badgeStatus = badgeStatus
        )
    }.map { response -> response.success }
}

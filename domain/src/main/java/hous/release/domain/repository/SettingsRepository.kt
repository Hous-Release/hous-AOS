package hous.release.domain.repository

interface SettingsRepository {
    suspend fun patchNotificationSettings(
        notificationStatus: Boolean?,
        newRulesStatus: String?,
        newTodoStatus: String?,
        startTodoStatus: String?,
        remindTodoStatus: String?,
        badgeStatus: String?
    ): Result<Boolean>
}

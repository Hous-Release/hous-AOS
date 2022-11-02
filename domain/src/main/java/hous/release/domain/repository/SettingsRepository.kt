package hous.release.domain.repository

interface SettingsRepository {
    suspend fun patchNotificationSettings(
        notificationStatus: Boolean? = null,
        newRulesStatus: String? = null,
        newTodoStatus: String? = null,
        startTodoStatus: String? = null,
        remindTodoStatus: String? = null,
        badgeStatus: String? = null
    ): Result<Boolean>
}

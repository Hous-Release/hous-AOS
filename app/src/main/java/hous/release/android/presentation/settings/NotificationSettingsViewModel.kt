package hous.release.android.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.NotificationStatusType
import hous.release.domain.entity.response.NotificationSettings
import hous.release.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _notificationSettingsInfo: MutableStateFlow<NotificationSettings> =
        MutableStateFlow(NotificationSettings())
    val notificationSettingsInfo: StateFlow<NotificationSettings> =
        _notificationSettingsInfo.asStateFlow()

    fun getNotificationSettings() {
        viewModelScope.launch {
            settingsRepository.getNotificationSettings()
                .onSuccess { response -> _notificationSettingsInfo.value = response }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchNotificationSettings(status: Boolean) {
        if (notificationSettingsInfo.value.isPushNotification != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(notificationStatus = status)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(isPushNotification = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }

    fun patchNewRulesSettings(status: NotificationStatusType) {
        if (notificationSettingsInfo.value.rulesPushStatus != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(newRulesStatus = status.name)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(rulesPushStatus = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }

    fun patchNewTodosSettings(status: NotificationStatusType) {
        if (notificationSettingsInfo.value.newTodoPushStatus != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(newTodoStatus = status.name)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(newTodoPushStatus = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }

    fun patchStartTodosSettings(status: NotificationStatusType) {
        if (notificationSettingsInfo.value.todayTodoPushStatus != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(startTodoStatus = status.name)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(todayTodoPushStatus = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }

    fun patchRemindTodosSettings(status: NotificationStatusType) {
        if (notificationSettingsInfo.value.remindTodoPushStatus != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(remindTodoStatus = status.name)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(remindTodoPushStatus = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }

    fun patchBadgeSettings(status: NotificationStatusType) {
        if (notificationSettingsInfo.value.badgePushStatus != status) {
            viewModelScope.launch {
                settingsRepository.patchNotificationSettings(badgeStatus = status.name)
                    .onSuccess {
                        _notificationSettingsInfo.value =
                            notificationSettingsInfo.value.copy(badgePushStatus = status)
                    }
                    .onFailure { Timber.d(it.message.toString()) }
            }
        }
    }
}

package hous.release.android.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.NotificationStatusType
import hous.release.domain.repository.SettingsRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun patchNotificationSettings(status: Boolean) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(notificationStatus = status)
                .onSuccess {
                    Timber.d("notification $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchNewRulesSettings(status: NotificationStatusType) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(newRulesStatus = status.name)
                .onSuccess {
                    Timber.d("newRules $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchNewTodosSettings(status: NotificationStatusType) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(newTodoStatus = status.name)
                .onSuccess {
                    Timber.d("newTodos $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchStartTodosSettings(status: NotificationStatusType) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(startTodoStatus = status.name)
                .onSuccess {
                    Timber.d("startTodos $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchRemindTodosSettings(status: NotificationStatusType) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(remindTodoStatus = status.name)
                .onSuccess {
                    Timber.d("remindTodos $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun patchBadgeSettings(status: NotificationStatusType) {
        viewModelScope.launch {
            settingsRepository.patchNotificationSettings(badgeStatus = status.name)
                .onSuccess {
                    Timber.d("badge $status $it")
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}

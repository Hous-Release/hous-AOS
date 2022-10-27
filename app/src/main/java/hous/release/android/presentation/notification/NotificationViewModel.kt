package hous.release.android.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.NotificationRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    fun getNotification() {
        viewModelScope.launch {
            notificationRepository.getNotification(Long.MAX_VALUE)
                .onSuccess {
                    Timber.d(it.toString())
                }
                .onFailure {
                    Timber.e(it.message.toString())
                }
        }
    }
}

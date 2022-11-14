package hous.release.android.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.NotificationContent
import hous.release.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _emptyNotification = MutableStateFlow<Boolean>(false)
    val emptyNotification: StateFlow<Boolean> = _emptyNotification.asStateFlow()

    fun initEmptyNotification(isEmpty: Boolean) {
        _emptyNotification.value = isEmpty
    }

    fun getNotification(): Flow<PagingData<NotificationContent>> =
        notificationRepository.getNotification(size = NOTIFICATION_LIST_SIZE)
            .cachedIn(viewModelScope)

    companion object {
        const val NOTIFICATION_LIST_SIZE = 10
    }
}

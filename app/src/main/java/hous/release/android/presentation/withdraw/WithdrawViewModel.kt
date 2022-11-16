package hous.release.android.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.FeedbackType
import hous.release.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var feedbackType = FeedbackType.NO

    val comment = MutableStateFlow("")

    val isCheckedWithdraw = MutableStateFlow(false)

    private val _isSuccessWithdraw = MutableSharedFlow<Boolean>()
    val isSuccessWithdraw: SharedFlow<Boolean> = _isSuccessWithdraw.asSharedFlow()

    fun initFeedbackType(type: FeedbackType) {
        feedbackType = type
    }

    fun deleteUser() {
        viewModelScope.launch {
            authRepository.deleteUser(feedbackType = feedbackType, comment = comment.value)
                .onSuccess { response ->
                    authRepository.clearLocalPref()
                    _isSuccessWithdraw.emit(response)
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}

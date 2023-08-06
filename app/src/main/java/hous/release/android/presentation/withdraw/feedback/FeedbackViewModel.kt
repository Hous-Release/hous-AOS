package hous.release.android.presentation.withdraw.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.UiEvent
import hous.release.domain.usecase.PostWithdrawFeedbackUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val postWithdrawFeedbackUseCase: PostWithdrawFeedbackUseCase
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _isSkip = MutableSharedFlow<Boolean>()
    val isSkip = _isSkip.asSharedFlow()

    val comment = MutableStateFlow("")

    private fun postFeedback() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.LOADING)
            postWithdrawFeedbackUseCase(comment = comment.value)
                .onSuccess {
                    _uiEvent.emit(UiEvent.SUCCESS)
                    comment.value = ""
                }.onFailure { throwable ->
                    Timber.e(throwable)
                }
        }
    }

    fun onClickDone() {
        viewModelScope.launch {
            if (comment.value.isEmpty()) {
                _isSkip.emit(true)
            } else {
                postFeedback()
            }
        }
    }
}

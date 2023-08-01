package hous.release.android.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    /** TODO 영주 : 피드백 api 유즈케이스 */
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val comment = MutableStateFlow("")

    fun postFeedback() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.LOADING)
            if (comment.value.isEmpty()) {
                Timber.e("건너뛰기")
                _uiEvent.emit(UiEvent.SUCCESS)
            } else {
                Timber.e("보내기")
                /** TODO 영주 : 피드백 api 호출 */
                _uiEvent.emit(UiEvent.SUCCESS)
            }
        }
    }
}
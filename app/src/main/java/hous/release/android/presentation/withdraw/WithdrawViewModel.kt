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
class WithdrawViewModel @Inject constructor(
    /** TODO 영주 : 탈퇴하기 api 유즈케이스 */
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val isCheckedWithdraw = MutableStateFlow(false)

    fun deleteUser() {
        viewModelScope.launch {
            Timber.e("로딩")
            _uiEvent.emit(UiEvent.LOADING)
            /** TODO 영주 : 탈퇴하기 api 호출*/
            Timber.e("탈퇴성공")
            _uiEvent.emit(UiEvent.SUCCESS)
        }
    }
}

package hous.release.android.presentation.withdraw.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.UiEvent
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.ClearLocalPrefUseCase
import hous.release.domain.usecase.DeleteUserUseCase
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val clearLocalPrefUseCase: ClearLocalPrefUseCase,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    val isCheckedWithdraw = MutableStateFlow(false)

    fun deleteUser() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.LOADING)
            deleteUserUseCase()
                .onSuccess {
                    clearLocalPrefUseCase()
                    setSplashStateUseCase(SplashState.LOGIN)
                    _uiEvent.emit(UiEvent.SUCCESS)
                }.onFailure { throwable ->
                    Timber.e(throwable)
                }
        }
    }
}

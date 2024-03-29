package hous.release.android.presentation.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    val isNextBtn = MutableStateFlow(false)

    private val _isTutorialEvent = MutableSharedFlow<Boolean>()
    val isTutorialState = _isTutorialEvent.asSharedFlow()

    fun nextOnClick() {
        viewModelScope.launch {
            setSplashStateUseCase(splashState = SplashState.LOGIN)
            _isTutorialEvent.emit(true)
        }
    }
}

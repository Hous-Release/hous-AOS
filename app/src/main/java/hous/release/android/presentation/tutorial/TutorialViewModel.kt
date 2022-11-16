package hous.release.android.presentation.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.SetSplashStateUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    val showNextBtn = MutableLiveData<Boolean>()

    private val _isTutorialState = MutableLiveData<Boolean>(false)
    val isTutorialState: LiveData<Boolean> = _isTutorialState

    fun nextOnClick() {
        viewModelScope.launch {
            setSplashStateUseCase(splashState = SplashState.LOGIN)
            _isTutorialState.value = true
        }
    }
}

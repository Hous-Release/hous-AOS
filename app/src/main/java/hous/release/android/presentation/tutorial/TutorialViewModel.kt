package hous.release.android.presentation.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.usecase.GetSkipTutorialUseCase
import hous.release.domain.usecase.InitSkipTutorialUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val getSkipTutorialUseCase: GetSkipTutorialUseCase,
    private val initSkipTutorialUseCase: InitSkipTutorialUseCase
) : ViewModel() {
    val showNextBtn = MutableLiveData<Boolean>()

    private val _isTutorialState = MutableLiveData<Boolean>()
    val isTutorialState: LiveData<Boolean> = _isTutorialState

    init {
        viewModelScope.launch {
            _isTutorialState.value = getSkipTutorialUseCase.invoke()
        }
    }

    fun nextOnClick() {
        viewModelScope.launch {
            initSkipTutorialUseCase.invoke(skipTutorial = true)
            _isTutorialState.value = true
        }
    }
}

package hous.release.android.presentation.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.data.datasource.TutorialDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val tutorialDataSource: TutorialDataSource
) : ViewModel() {
    val showNextBtn = MutableLiveData<Boolean>()

    private val _isSkipTutorial = MutableLiveData<Boolean>()
    val skipTutorial: LiveData<Boolean> = _isSkipTutorial

    init {
        viewModelScope.launch {
            if (tutorialDataSource.getShowTutorial()) {
                _isSkipTutorial.value = true
            }
        }
    }

    fun nextOnClick() {
        viewModelScope.launch {
            tutorialDataSource.setShowTutorial(skipTutorial = true)
        }
    }
}

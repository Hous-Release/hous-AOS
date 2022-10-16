package hous.release.android.presentation.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.data.datasource.SharedPrefDataSource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val sharedPrefDataSource: SharedPrefDataSource
) : ViewModel() {
    val showNextBtn = MutableLiveData<Boolean>()

    private val _isTutorialState = MutableLiveData<Boolean>()
    val isTutorialState: LiveData<Boolean> = _isTutorialState

    init {
        viewModelScope.launch {
            _isTutorialState.value = sharedPrefDataSource.getShowTutorial()
        }
    }

    fun nextOnClick() {
        viewModelScope.launch {
            sharedPrefDataSource.initShowTutorial(skipTutorial = true)
            _isTutorialState.value = true
        }
    }
}

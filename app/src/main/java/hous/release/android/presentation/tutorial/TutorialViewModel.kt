package hous.release.android.presentation.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TutorialViewModel : ViewModel() {
    val showNextBtn = MutableLiveData<Boolean>()
}

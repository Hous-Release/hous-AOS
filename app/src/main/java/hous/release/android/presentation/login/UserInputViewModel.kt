package hous.release.android.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {
    private val _nickname = MutableLiveData<String>()
    val nickname get() = _nickname

    private val _birthday = MutableLiveData<String>()
    val birthday get() = _birthday
}

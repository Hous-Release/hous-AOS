package hous.release.android.presentation.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hous.release.android.util.extension.Event

class UserInputViewModel : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData<String>("1999-08-02")

    private val _isInputUserInfo = MediatorLiveData<Event<Boolean>>().apply {
        addSource(nickname) { nickname ->
            value = Event(nickname.isNotBlank() && birthday.value != null)
        }
        addSource(birthday) { birthday ->
            value = Event(birthday.isNotBlank() && nickname.value != null)
        }
    }
    val isInputUserInfo get() = _isInputUserInfo

    private val _isBtnCheckBirthday = MutableLiveData(false)
    val isBtnCheckBirthday get() = _isBtnCheckBirthday
}

package hous.release.android.presentation.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hous.release.android.util.extension.Event

class UserInputViewModel : ViewModel() {
    private val _nickname = MutableLiveData<String>()
    val nickname get() = _nickname

    private val _birthday = MutableLiveData<String>()
    val birthday get() = _birthday

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

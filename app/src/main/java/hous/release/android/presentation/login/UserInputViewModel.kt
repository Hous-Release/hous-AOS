package hous.release.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData<String>("1999-08-02")

    private val _isBtnCheckBirthday = MutableLiveData(false)
    val isisBtnCheckBirthday: LiveData<Boolean> = _isBtnCheckBirthday
}

package hous.release.android.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInputViewModel : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData<String>("1999-08-02")

    val isCheckBirthday = MutableLiveData<Boolean>()
}

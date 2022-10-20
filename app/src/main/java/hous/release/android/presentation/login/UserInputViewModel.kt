package hous.release.android.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.AuthRepository

class UserInputViewModel : ViewModel() {
@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData<String>("1999-08-02")

    val isCheckBirthday = MutableLiveData<Boolean>()
}

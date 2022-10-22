package hous.release.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.usecase.PostSignUpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val postSignUpUseCase: PostSignUpUseCase
) : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData<String>("1999-08-02")

    val isCheckBirthday = MutableLiveData<Boolean>()

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> = _isSignUp

    fun nextOnClick() {
        viewModelScope.launch {
            postSignUpUseCase.invoke(
                birthday = birthday.value!!,
                fcmToken = "",
                isPublic = isCheckBirthday.value!!,
                nickname = nickname.value!!,
                socialType = "",
                token = ""
            ).onSuccess {
                _isSignUp.value = true
            }.onFailure {
                _isSignUp.value = false
            }
        }
    }
}

package hous.release.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.usecase.InitHousTokenUseCase
import hous.release.domain.usecase.PostSignUpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val postSignUpUseCase: PostSignUpUseCase,
    private val initHousTokenUseCase: InitHousTokenUseCase
) : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData("")

    val isPrivateBirthday = MutableLiveData(false)

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> = _isSignUp

    fun nextOnClick() {
        viewModelScope.launch {
            postSignUpUseCase.invoke(
                birthday = requireNotNull(birthday.value),
                isPublic = requireNotNull(isPrivateBirthday.value),
                nickname = requireNotNull(nickname.value)
            ).onSuccess { response ->
                initHousTokenUseCase(response.token)
                _isSignUp.value = true
            }.onFailure {
                _isSignUp.value = false
            }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth
    }
}

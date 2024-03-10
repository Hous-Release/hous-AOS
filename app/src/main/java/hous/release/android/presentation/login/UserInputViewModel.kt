package hous.release.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.InitHousTokenUseCase
import hous.release.domain.usecase.PostSignUpUseCase
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInputViewModel @Inject constructor(
    private val postSignUpUseCase: PostSignUpUseCase,
    private val initHousTokenUseCase: InitHousTokenUseCase,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    val nickname = MutableStateFlow("")
    val birthday = MutableStateFlow("")
    val isPrivateBirthday = MutableStateFlow(false)

    private val _isSignedUp = MutableSharedFlow<Boolean>()
    val isSignedUp = _isSignedUp.asSharedFlow()

    fun updateIsPrivateBirthday() {
        isPrivateBirthday.value = !isPrivateBirthday.value
    }

    fun signUp() {
        viewModelScope.launch {
            postSignUpUseCase(
                birthday = birthday.value,
                isPublic = !isPrivateBirthday.value,
                nickname = nickname.value
            ).onSuccess { response ->
                initHousTokenUseCase(response.token)
                setSplashStateUseCase(SplashState.ENTER_ROOM)
                _isSignedUp.emit(true)
            }.onFailure {
                _isSignedUp.emit(false)
            }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth
    }
}

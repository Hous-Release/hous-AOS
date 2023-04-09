package hous.release.android.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.KakaoLoginCallback
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.GetFcmTokenUseCase
import hous.release.domain.usecase.InitHousTokenUseCase
import hous.release.domain.usecase.InitLoginTokenUseCase
import hous.release.domain.usecase.PostForceLoginUseCase
import hous.release.domain.usecase.PostLoginUseCase
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val initHousTokenUseCase: InitHousTokenUseCase,
    private val initLoginTokenUseCase: InitLoginTokenUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    private val postForceLoginUseCase: PostForceLoginUseCase,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    private val kakaoToken = MutableStateFlow("")
    private val fcmToken = MutableStateFlow("")

    private val _isSignedUp = MutableSharedFlow<Boolean>()
    val isSignedUp = _isSignedUp.asSharedFlow()

    private val _isJoiningRoom = MutableSharedFlow<Boolean>()
    val isJoiningRoom = _isJoiningRoom.asSharedFlow()

    private val _isMultipleAccess = MutableStateFlow(false)
    val isMiultipleAccess = _isMultipleAccess.asStateFlow()

    val isKakaoLogin = combine(kakaoToken, fcmToken) { kakaoToken, fcmToken ->
        kakaoToken.isNotBlank() && fcmToken.isNotBlank()
    }

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        KakaoLoginCallback { accessToken ->
            kakaoToken.value = accessToken
        }.handleResult(token, error)
    }

    init {
        getFCMToken()
    }

    fun postLogin() {
        viewModelScope.launch {
            postLoginUseCase(
                fcmToken = fcmToken.value,
                socialType = SOCIAL_TYPE,
                token = kakaoToken.value
            ).onSuccess { response ->
                initHousTokenUseCase(token = response.token)
                _isJoiningRoom.emit(response.isJoiningRoom)
                setSplashStateUseCase(
                    if (response.isJoiningRoom) SplashState.MAIN
                    else SplashState.ENTER_ROOM
                )
            }.onFailure { throwable ->
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        NOT_SIGN_UP -> {
                            initLoginTokenUseCase(
                                fcmToken = fcmToken.value,
                                socialType = SOCIAL_TYPE,
                                token = kakaoToken.value
                            )
                            _isSignedUp.emit(false)
                            Timber.e(throwable.message)
                        }
                        ALREADY_LOGIN -> {
                            _isMultipleAccess.value = true
                        }
                        else -> {
                            Timber.e(throwable.message)
                        }
                    }
                }
            }
        }
    }

    fun postForceLogin() {
        viewModelScope.launch {
            postForceLoginUseCase(
                fcmToken = fcmToken.value,
                socialType = SOCIAL_TYPE,
                token = kakaoToken.value
            ).onSuccess { response ->
                _isJoiningRoom.emit(response.isJoiningRoom)
                initHousTokenUseCase(token = response.token)
                setSplashStateUseCase(
                    if (response.isJoiningRoom) SplashState.MAIN
                    else SplashState.ENTER_ROOM
                )
            }.onFailure { throwable ->
                Timber.e(throwable.message)
            }
        }
    }

    private fun getFCMToken() {
        viewModelScope.launch {
            getFcmTokenUseCase { getFcmToken -> fcmToken.value = getFcmToken }
        }
    }

    companion object {
        private const val SOCIAL_TYPE = "KAKAO"
        private const val NOT_SIGN_UP = 404
        private const val ALREADY_LOGIN = 409
    }
}

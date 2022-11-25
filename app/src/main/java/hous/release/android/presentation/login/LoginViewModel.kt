package hous.release.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.extension.Event
import hous.release.domain.usecase.GetFcmTokenUseCase
import hous.release.domain.usecase.InitHousTokenUseCase
import hous.release.domain.usecase.InitTokenUseCase
import hous.release.domain.usecase.PostForceLoginUseCase
import hous.release.domain.usecase.PostLoginUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val initTokenUseCase: InitTokenUseCase,
    private val initHousTokenUseCase: InitHousTokenUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
    private val postForceLoginUseCase: PostForceLoginUseCase
) : ViewModel() {
    private val kakaoToken = MutableLiveData<String>()

    private val fcmToken = MutableLiveData<String>()

    private val _isSuccessKakaoLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessKakaoLogin: LiveData<Event<Boolean>> = _isSuccessKakaoLogin

    private val _isJoiningRoom = MutableLiveData<Boolean>()
    val isJoiningRoom: LiveData<Boolean> = _isJoiningRoom

    private val _isUser = MutableLiveData<Boolean>()
    val isUser: LiveData<Boolean> = _isUser

    private val _isMultipleAccess = MutableLiveData<Boolean>()
    val isMultipleAccess: LiveData<Boolean> = _isMultipleAccess

    private val _isInitUserInfo = MediatorLiveData<Event<Boolean>>().apply {
        addSource(kakaoToken) { token ->
            value = Event(token.isNotBlank() && fcmToken.value != null)
        }
        addSource(fcmToken) { token ->
            value = Event(token.isNotBlank() && kakaoToken.value != null)
        }
    }
    val isInitUserInfo get() = _isInitUserInfo

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Timber.e(error, "접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Timber.e(error, "유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Timber.e(error, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Timber.e(error, "요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Timber.e(error, "유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Timber.e(error, "설정이 올바르지 않음(android key hash)")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Timber.e(error, "서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Timber.e(error, "앱이 요청 권한이 없음")
                }
                else -> {
                    Timber.e(error, "기타 에러")
                }
            }
        } else if (token != null) {
            Timber.e("카카오 로그인 성공 ${token.accessToken}")
            kakaoToken.value = token.accessToken
            _isSuccessKakaoLogin.value = Event(true)
        }
    }

    init {
        getFCMToken()
    }

    fun postLogin() {
        viewModelScope.launch {
            postLoginUseCase(
                fcmToken = requireNotNull(fcmToken.value),
                socialType = "KAKAO",
                token = requireNotNull(kakaoToken.value)
            ).onSuccess { response ->
                initTokenUseCase(
                    fcmToken = requireNotNull(fcmToken.value),
                    socialType = SOCIAL_TYPE,
                    token = requireNotNull(kakaoToken.value)
                )
                initHousTokenUseCase(
                    token = response.token
                )
                _isJoiningRoom.value = response.isJoiningRoom
            }.onFailure { throwable ->
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        NOT_SIGN_UP -> {
                            initTokenUseCase(
                                fcmToken = requireNotNull(fcmToken.value),
                                socialType = SOCIAL_TYPE,
                                token = requireNotNull(kakaoToken.value)
                            )
                            _isUser.value = false
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

    fun initIsPermitAccess() {
        viewModelScope.launch {
            postForceLoginUseCase(
                fcmToken = requireNotNull(fcmToken.value),
                socialType = "KAKAO",
                token = requireNotNull(kakaoToken.value)
            ).onSuccess { response ->
                _isJoiningRoom.value = response.isJoiningRoom
                initTokenUseCase(
                    fcmToken = requireNotNull(fcmToken.value),
                    socialType = SOCIAL_TYPE,
                    token = requireNotNull(kakaoToken.value)
                )
                initHousTokenUseCase(
                    token = response.token
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

        // private const val INVALID_TOKEN = 401
    }
}

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
    private val getFcmTokenUseCase: GetFcmTokenUseCase
) : ViewModel() {
    private val _kakaoToken = MutableLiveData<String>()
    val kakaoToken: LiveData<String> = _kakaoToken

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken: LiveData<String> = _fcmToken

    private val _isSuccessKakaoLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessKakaoLogin: LiveData<Event<Boolean>> = _isSuccessKakaoLogin

    private val _isJoiningRoom = MutableLiveData<Boolean>()
    val isJoiningRoom: LiveData<Boolean> = _isJoiningRoom

    private val _isUser = MutableLiveData<Boolean>()
    val isUser: LiveData<Boolean> = _isUser

    // 카카오중복로그인 처리할 때 사용할 변수
    private val _isSameToken = MutableLiveData<Boolean>()
    val isSameToken: LiveData<Boolean> = _isSameToken

    private val _isInitUserInfo = MediatorLiveData<Event<Boolean>>().apply {
        addSource(_kakaoToken) { token ->
            value = Event(token.isNotBlank() && _fcmToken.value != null)
        }
        addSource(_fcmToken) { token ->
            value = Event(token.isNotBlank() && _kakaoToken.value != null)
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
            _kakaoToken.value = token.accessToken
            _isSuccessKakaoLogin.value = Event(true)
        }
    }

    init {
        getFCMToken()
    }

    fun postLogin() {
        viewModelScope.launch {
            postLoginUseCase(
                fcmToken = requireNotNull(_fcmToken.value),
                socialType = "KAKAO",
                token = requireNotNull(_kakaoToken.value)
            ).onSuccess { response ->
                initTokenUseCase(
                    fcmToken = requireNotNull(_fcmToken.value),
                    socialType = SOCIAL_TYPE,
                    token = _kakaoToken.value!!
                )
                initHousTokenUseCase(
                    token = response.token
                )
                if (response.isJoiningRoom) {
                    _isJoiningRoom.value = true
                    Timber.e("로그인 성공 / 방 있음")
                } else {
                    _isJoiningRoom.value = false
                    Timber.e("로그인 성공 / 방 없음")
                }
            }.onFailure { throwable ->
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        USER_NOT_EXIST -> {
                            initTokenUseCase(
                                fcmToken = requireNotNull(_fcmToken.value),
                                socialType = SOCIAL_TYPE,
                                token = _kakaoToken.value!!
                            )
                            _isUser.value = false
                            Timber.e(throwable.message)
                        }
                        else -> {
                            Timber.e(throwable.message)
                        }
                    }
                }
            }
        }
    }

    private fun getFCMToken() {
        viewModelScope.launch {
            getFcmTokenUseCase { fcmToken -> _fcmToken.value = fcmToken }
        }
    }

    companion object {
        private const val SOCIAL_TYPE = "KAKAO"

        // private const val ALREADY_LOGIN = 409
        // private const val INVALID_TOKEN = 401
        private const val USER_NOT_EXIST = 404
    }
}

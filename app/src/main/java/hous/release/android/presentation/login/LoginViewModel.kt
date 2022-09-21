package hous.release.android.presentation.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.extension.Event
import hous.release.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _kakaoToken = MutableLiveData<String>()
    val kakaoToken get() = _kakaoToken

    private val _fcmToken = MutableLiveData<String>()
    val fcmToken get() = _fcmToken

    private val _isSuccessKakaoLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessKakaoLogin get() = _isSuccessKakaoLogin

    private val _isSuccessLogin = MutableLiveData<Event<Boolean>>()
    val isSuccessLogin get() = _isSuccessLogin

    private val _isInitUserInfo = MediatorLiveData<Event<Boolean>>().apply {
        addSource(_kakaoToken) { token ->
            value = Event(token.isNotBlank() && fcmToken.value != null)
        }
        addSource(_fcmToken) { token ->
            value = Event(token.isNotBlank() && kakaoToken.value != null)
        }
    }
    val isInitUserInfo get() = _isInitUserInfo

    val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Timber.d(error, "접근이 거부 됨(동의 취소)")
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Timber.d(error, "유효하지 않은 앱")
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Timber.d(error, "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Timber.d(error, "요청 파라미터 오류")
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Timber.d(error, "유효하지 않은 scope ID")
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Timber.d(error, "설정이 올바르지 않음(android key hash)")
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Timber.d(error, "서버 내부 에러")
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Timber.d(error, "앱이 요청 권한이 없음")
                }
                else -> {
                    Timber.d(error, "기타 에러")
                }
            }
        } else if (token != null) {
            Timber.d("카카오 로그인 성공 ${token.accessToken}")
            _fcmToken.value = ""
            _kakaoToken.value = token.accessToken
            _isSuccessKakaoLogin.value = Event(true)
        }
    }

    fun postLogin() {
        viewModelScope.launch {
            authRepository.postLogin(
                fcmToken = "hello world",
                socialType = "KAKAO",
                token = requireNotNull(kakaoToken.value)
            ).onSuccess { response ->
                Timber.d("로그인 성공")
                _isSuccessLogin.postValue(Event(true))
            }.onFailure {
                Timber.d("로그인 실패")
            }
        }
    }
}

package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import hous.release.android.R
import hous.release.android.databinding.ActivityLoginBinding
import hous.release.android.util.binding.BindingActivity
import timber.log.Timber

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var kakaoLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kakaoLogin()
    }

    private fun kakaoLogin() {
        kakaoLogin = binding.btnLoginKakaoLogin
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Timber.d(error, "토큰 정보 보기 실패")
            } else if (tokenInfo != null) {
                Timber.d("토큰 정보 보기 성공")
                val intent = Intent(this, UserInputActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
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
                Timber.d("로그인 성공")
                val intent = Intent(this, UserInputActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
        kakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}

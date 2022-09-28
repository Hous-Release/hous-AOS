package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityLoginBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.EventObserver
import hous.release.data.service.KakaoLoginService
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLoginBtnClickListener()
        initIsSuccessKakaoLoginObserver()
        initIsInitUserInfoObserver()
        initIsSucessLoginObserver()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnLoginKakao.setOnClickListener {
            startKakaoLogin()
        }
    }

    private fun startKakaoLogin() {
        kakaoLoginService.startKakaoLogin(loginViewModel.kakaoLoginCallback)
    }

    private fun initIsSuccessKakaoLoginObserver() {
        loginViewModel.isSuccessKakaoLogin.observe(
            this,
            EventObserver { isSuccess ->
                if (isSuccess) {
                    Timber.d("카카오 로그인 성공")
                } else {
                    Timber.d("카카오 로그인 실패")
                }
            }
        )
    }

    private fun initIsInitUserInfoObserver() {
        loginViewModel.isInitUserInfo.observe(
            this,
            EventObserver { isSuccess ->
                if (isSuccess) loginViewModel.postLogin()
            }
        )
    }

    private fun initIsSucessLoginObserver() {
        loginViewModel.isSuccessLogin.observe(
            this,
            EventObserver { isSuccess ->
                if (isSuccess) {
                    Timber.d("로그인 성공")
                    startActivity(Intent(this, UserInputActivity::class.java))
                } else {
                    Timber.d("로그인 실패")
                }
            }
        )
    }
}

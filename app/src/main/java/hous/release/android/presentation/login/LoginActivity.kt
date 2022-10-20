package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityLoginBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.EventObserver
import hous.release.android.util.showToast
import hous.release.data.service.KakaoLoginService
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val loginViewModel: LoginViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLoginBtnClickListener()
        initIsSuccessKakaoLoginObserver()
        initIsInitUserInfoObserver()
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                        onBackPressedTime = System.currentTimeMillis()
                        showToast(getString(R.string.finish_app_toast_msg))
                    } else {
                        finishAffinity()
                        System.runFinalization()
                        exitProcess(0)
                    }
                }
            }
        )
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
                    Timber.e("카카오 로그인 성공")
                } else {
                    Timber.e("카카오 로그인 실패")
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

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}

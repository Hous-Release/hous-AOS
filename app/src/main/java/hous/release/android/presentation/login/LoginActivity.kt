package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityLoginBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.presentation.main.MainActivity
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
        initIsJoiningRoomObserve()
        initIsUserObserve()
    }

    private fun initIsUserObserve() {
        loginViewModel.isJoiningRoom.observe(this) {
            if (loginViewModel.isJoiningRoom.value == true) {
                val toMain = Intent(this, MainActivity::class.java)
                startActivity(toMain)
                finishAffinity()
                Timber.e("메인으로 이동")
            } else {
                val toEnterRoom = Intent(this, EnterRoomActivity::class.java)
                startActivity(toEnterRoom)
                finishAffinity()
                Timber.e("방입장으로 이동")
            }
        }
    }

    private fun initIsJoiningRoomObserve() {
        loginViewModel.isUser.observe(this) {
            if (loginViewModel.isUser.value == false) {
                val toUserInput = Intent(this, UserInputActivity::class.java)
                startActivity(toUserInput)
                finishAffinity()
                Timber.e("정보입력으로 이동")
            }
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
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

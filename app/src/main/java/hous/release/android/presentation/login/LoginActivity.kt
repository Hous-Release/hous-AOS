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
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.data.service.KakaoLoginService
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
        initBackPressedCallback()
        collectIsJoiningRoom()
        collectIsSignedUp()
        collectIsKakaoLogin()
        collectIsMiultipleAccess()
    }

    private fun collectIsJoiningRoom() {
        repeatOnStarted {
            loginViewModel.isJoiningRoom.collect { joiningRoom ->
                if (joiningRoom) {
                    ToastMessageUtil.showToast(this@LoginActivity, getString(R.string.login_toast))
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    startActivity(Intent(this@LoginActivity, EnterRoomActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun collectIsSignedUp() {
        repeatOnStarted {
            loginViewModel.isSignedUp.collect { signedUp ->
                if (!signedUp) {
                    startActivity(Intent(this@LoginActivity, UserInputActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@LoginActivity,
                    getString(R.string.finish_app_toast_msg)
                )
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

    private fun collectIsKakaoLogin() {
        repeatOnStarted {
            loginViewModel.isKakaoLogin.collect { success ->
                if (success) {
                    loginViewModel.postLogin()
                }
            }
        }
    }

    private fun collectIsMiultipleAccess() {
        repeatOnStarted {
            loginViewModel.isMiultipleAccess.collect { accessLogin ->
                if (accessLogin) {
                    WarningDialogFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable(
                                WarningDialogFragment.WARNING_TYPE,
                                WarningType.WARNING_SPLASH
                            )
                            putParcelable(
                                WarningDialogFragment.CONFIRM_ACTION,
                                ConfirmClickListener(confirmAction = { loginViewModel.postForceLogin() })
                            )
                        }
                    }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
                }
            }
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}

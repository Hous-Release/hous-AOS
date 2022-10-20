package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityUserInputBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.showToast
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val userInputViewModel: UserInputViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = userInputViewModel
        initNextBtnOnClickListener()
        initBackPressedCallback()
        initSignUpObserve()
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

    private fun initSignUpObserve() {
        userInputViewModel.isSignUp.observe(this) {
            if (userInputViewModel.isSignUp.value == true) {
                val toEnterRoom = Intent(this, EnterRoomActivity::class.java)
                startActivity(toEnterRoom)
                finishAffinity()
                Timber.e("방입장으로 이동")
            } else {
                // 에러 시에 띄울 뷰
            }
        }
    }

    private fun initNextBtnOnClickListener() {
        binding.btnUserInputNext.setOnClickListener {
            val toEnterRoom = Intent(this, EnterRoomActivity::class.java)
            startActivity(toEnterRoom)
            finishAffinity()
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}

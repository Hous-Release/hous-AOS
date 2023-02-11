package hous.release.android.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityUserInputBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.DatePickerClickListener
import hous.release.android.util.dialog.DatePickerDialog
import hous.release.android.util.dialog.WarningDialogFragment.Companion.CONFIRM_ACTION
import hous.release.android.util.extension.repeatOnStarted
import kotlin.system.exitProcess

@AndroidEntryPoint
class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val userInputViewModel: UserInputViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = userInputViewModel
        initSignUpCollect()
        initNextBtnOnClickListener()
        initBackPressedCallback()
        initBirthdayOnClickListener()
        initEditTextClearFocus()
        initKeyboardNextClickListener()
    }

    private fun initSignUpCollect() {
        repeatOnStarted {
            userInputViewModel.isSignUp.collect { success ->
                if (success) {
                    startActivity(Intent(this, EnterRoomActivity::class.java))
                    finishAffinity()
                }
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

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                        onBackPressedTime = System.currentTimeMillis()
                        ToastMessageUtil.showToast(
                            this@UserInputActivity,
                            getString(R.string.finish_app_toast_msg)
                        )
                    } else {
                        finishAffinity()
                        System.runFinalization()
                        exitProcess(0)
                    }
                }
            }
        )
    }

    private fun initBirthdayOnClickListener() {
        binding.etUserInputBirthday.setOnClickListener {
            DatePickerDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        CONFIRM_ACTION,
                        DatePickerClickListener(
                            confirmActionWithDate = { date ->
                                userInputViewModel.initSelectedBirthDate(date)
                            }
                        )
                    )
                }
            }.show(supportFragmentManager, SELECT_BIRTHDAY)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clUserInput.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = this)
            return@setOnTouchListener false
        }
    }

    private fun initKeyboardNextClickListener() {
        binding.etUserInputNickname.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.etUserInputBirthday.performClick()
                handled = true
            }
            KeyBoardUtil.hide(activity = this)
            handled
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
        private const val SELECT_BIRTHDAY = "select birthday"
    }
}

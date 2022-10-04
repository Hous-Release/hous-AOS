package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityUserInputBinding
import hous.release.android.presentation.tutorial.TutorialActivity
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.EventObserver

@AndroidEntryPoint
class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val userInputViewModel: UserInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = userInputViewModel
        observeInputState()
        initBackBtnOnClickListener()
        initCheckBirthdayBtnOnClickListener()
    }

    private fun observeInputState() {
        userInputViewModel.isInputUserInfo.observe(
            this,
            EventObserver { inputAll ->
                if (inputAll) {
                    initChangeBtnNextColor()
                    initNextBtnOnClickListener()
                }
            }
        )
    }

    private fun initBackBtnOnClickListener() {
        binding.btnUserInputBack.setOnClickListener {
            finish()
        }
    }

    private fun initCheckBirthdayBtnOnClickListener() {
        binding.cbCheckBirthday.setOnClickListener {
            userInputViewModel.isBtnCheckBirthday.value = binding.cbCheckBirthday.isChecked
        }
    }

    private fun initChangeBtnNextColor() {
        binding.tvUserInputNext.backgroundTintList = this.getColorStateList(R.color.hous_blue)
    }

    private fun initNextBtnOnClickListener() {
        binding.tvUserInputNext.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java).apply {
                putExtra("nickname", userInputViewModel.nickname.value)
                putExtra("birthday", userInputViewModel.birthday.value)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}

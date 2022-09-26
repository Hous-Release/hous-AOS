package hous.release.android.presentation.login

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityUserInputBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.EventObserver

@AndroidEntryPoint
class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val userInputViewModel: UserInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = userInputViewModel
        initIsInputUserInfoObserver()
        initBtnBackOnClickListener()
    private fun initIsInputUserInfoObserver() {
        userInputViewModel.isInputUserInfo.observe(
            this,
            EventObserver { full ->
                if (full) {
                    initChangeBtnNextColor()
                    initBtnNextOnClickListener()
                }
            }
        )
    }

    private fun initBtnBackOnClickListener() {
        binding.btnUserInputBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initBtnCheckBirthdayOnClickListener() {
        binding.cbCheckBirthday.setOnClickListener {
            userInputViewModel.isBtnCheckBirthday.value = binding.cbCheckBirthday.isChecked
        }
    }

    private fun initChangeBtnNextColor() {
        binding.tvUserInputNext.backgroundTintList = this.getColorStateList(R.color.hous_blue)
    }
    }
}

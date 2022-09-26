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
    }
}

package hous.release.android.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityUserInputBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class UserInputActivity : BindingActivity<ActivityUserInputBinding>(R.layout.activity_user_input) {
    private val userInputViewModel: UserInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = userInputViewModel
        initNextBtnOnClickListener()
        initBackBtnOnClickListener()
        initCheckBirthdayBtnOnClickListener()
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

    private fun initNextBtnOnClickListener() {
        binding.tvUserInputNext.setOnClickListener {
            val toEnterRoom = Intent(this, EnterRoomActivity::class.java)
            startActivity(toEnterRoom)
            finishAffinity()
        }
    }
}

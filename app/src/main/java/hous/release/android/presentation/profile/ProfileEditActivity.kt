package hous.release.android.presentation.profile

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityProfileEditBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class ProfileEditActivity : BindingActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}    private fun initBirthdayOnClickListener() {
        binding.etProfileEditBirthday.setOnClickListener {
            val birthdayInt = initDateFormatToInt(profileEditViewModel.birthday.value!!)
            DatePickerDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        DatePickerClickListener(
                            confirmActionWithDate = { date -> initDate(date) },
                            birtyYear = birthdayInt[0],
                            birthMonth = birthdayInt[1],
                            birthDay = birthdayInt[2]
                        )
                    )
                }
                Timber.e("$birthdayInt")
            }.show(supportFragmentManager, SELECT_BIRTHDAY)
        }
    }
    private fun initDate(date: String) {
        profileEditViewModel.initSelectedBirthDate(date)
    }

    private fun initDateFormatToInt(birthday: String): List<Int> {
        val birthdayList: List<String> = birthday.split("-")
        val year = birthdayList[0].toInt()
        val month = birthdayList[1].toInt()
        val day = birthdayList[2].toInt()
        Timber.e("$year, $month, $day")
        return listOf(year, month, day)
    }

    companion object {
        private const val SELECT_BIRTHDAY = "select birthday"
    }

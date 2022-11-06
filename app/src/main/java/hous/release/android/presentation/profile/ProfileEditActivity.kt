package hous.release.android.presentation.profile

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityProfileEditBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.*
import timber.log.Timber

@AndroidEntryPoint
class ProfileEditActivity :
    BindingActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    private val profileEditViewModel: ProfileEditViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = profileEditViewModel
        initProfileData()
        initBirthdayTextAppearance()
        initSaveBtnOnClickListener()
        initBackBtnOnClickListener()
        initBirthdayOnClickListener()
        initBirthdayPublicOnClickListener()
        initIntroductionLength()
    }

    private fun initProfileData() {
        val nickname = intent.getStringExtra(ProfileFragment.NICKNAME)
        val birthday = intent.getStringExtra(ProfileFragment.BIRTHDAY)
        val isBirthday = intent.getBooleanExtra(ProfileFragment.ISBIRTHDAY, false)
        val mbti = intent.getStringExtra(ProfileFragment.MBTI)
        val job = intent.getStringExtra(ProfileFragment.JOB)
        val introduction = intent.getStringExtra(ProfileFragment.INTRODUCTION)
        profileEditViewModel.initData(
            nickname!!,
            birthday!!.replace(DOT, DASH),
            isBirthday,
            mbti,
            job,
            introduction
        )

        with(binding) {
            tvProfileEditBirthdayPublic.isSelected = isBirthday
            ivProfileEditBirthdayCheck.isSelected = isBirthday
        }
        Timber.e("${profileEditViewModel.nickname.value}, ${profileEditViewModel.birthday.value}, ${profileEditViewModel.isBirthdayPublic.value}, ${profileEditViewModel.mbti.value}, ${profileEditViewModel.job.value}, ${profileEditViewModel.introduction.value}")
    }

    private fun initBirthdayTextAppearance() {
        profileEditViewModel.birthday.observe(this) { birthday ->
            if (birthday == null) {
                binding.etProfileEditBirthday.setTextAppearance(R.style.B1)
            } else {
                binding.etProfileEditBirthday.setTextAppearance(R.style.En3)
            }
        }
    }

    private fun initSaveBtnOnClickListener() {
        profileEditViewModel.isSuccessEditProfile.observe(this) { isSuccess ->
            if (isSuccess) {
                finish()
            } else {
                // 에러 시에 띄울 뷰
            }
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnProfileEditBack.setOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_EDIT_PROFILE
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(confirmAction = { finish() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initBirthdayOnClickListener() {
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

    private fun initBirthdayPublicOnClickListener() {
        binding.llProfileEditBirthdayPublic.setOnClickListener {
            binding.tvProfileEditBirthdayPublic.isSelected =
                !binding.tvProfileEditBirthdayPublic.isSelected
            binding.ivProfileEditBirthdayCheck.isSelected =
                !binding.ivProfileEditBirthdayCheck.isSelected
            profileEditViewModel.initBirthdayPublic(binding.tvProfileEditBirthdayPublic.isSelected)
        }
    }

    private fun initIntroductionLength() {
        profileEditViewModel.introduction.observe(this) { introduction ->
            profileEditViewModel.initIntroductionLength(introduction!!.length)
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
        private const val DOT = "."
        private const val DASH = "-"
    }
}

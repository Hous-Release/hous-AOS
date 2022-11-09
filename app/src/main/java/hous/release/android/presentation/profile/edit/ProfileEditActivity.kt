package hous.release.android.presentation.profile.edit

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityProfileEditBinding
import hous.release.android.presentation.profile.ProfileFragment.Companion.PROFILE
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.DatePickerClickListener
import hous.release.android.util.dialog.DatePickerDialog
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
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
        initSaveBtnColor()
    }

    private fun initProfileData() {
        val profileData = intent.getParcelableExtra<ProfileEntity>(PROFILE)
        profileEditViewModel.initData(profileData!!)

        with(binding) {
            tvProfileEditBirthdayPublic.isSelected = profileData.birthdayPublic
            ivProfileEditBirthdayCheck.isSelected = profileData.birthdayPublic
        }
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
        profileEditViewModel.isEditProfile.observe(this) { isSuccess ->
            if (isSuccess) {
                finish()
            } else {
                // 에러 시에 띄울 뷰
            }
        }
    }

    private fun initSaveBtnColor() {
        profileEditViewModel.nickname.observe(this) { nickname ->
            if (nickname.isNullOrEmpty()) {
                binding.tvProfileEditSave.setTextColor(getColor(R.color.hous_g_4))
            } else {
                binding.tvProfileEditSave.setTextColor(getColor(R.color.hous_blue))
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
            with(binding) {
                tvProfileEditBirthdayPublic.isSelected =
                    !tvProfileEditBirthdayPublic.isSelected
                ivProfileEditBirthdayCheck.isSelected =
                    !ivProfileEditBirthdayCheck.isSelected
            }
            profileEditViewModel.initBirthdayPublic(binding.tvProfileEditBirthdayPublic.isSelected)
        }
    }

    private fun initDate(date: String) {
        profileEditViewModel.initSelectedBirthDate(date)
    }

    private fun initDateFormatToInt(birthday: String) =
        birthday.split("/").map { it.toInt() }

    companion object {
        private const val SELECT_BIRTHDAY = "select birthday"
    }
}

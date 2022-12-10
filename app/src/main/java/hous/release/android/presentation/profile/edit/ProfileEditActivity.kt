package hous.release.android.presentation.profile.edit

import android.os.Bundle
import androidx.activity.addCallback
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
import hous.release.android.util.extension.withArgs

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
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (profileEditViewModel.changedEditInfo.value == true) {
                WarningDialogFragment().withArgs {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_EDIT_PROFILE
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(confirmAction = { finish() })
                    )
                }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
            } else if (profileEditViewModel.changedEditInfo.value == false) {
                finish()
            }
        }
    }

    private fun initBackBtnOnClickListener() {
        binding.btnProfileEditBack.setOnClickListener {
            if (profileEditViewModel.changedEditInfo.value == true) {
                WarningDialogFragment().withArgs {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_EDIT_PROFILE
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(confirmAction = { finish() })
                    )
                }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
            } else {
                finish()
            }
        }
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
            }
        }
    }

    private fun initBirthdayOnClickListener() {
        binding.etProfileEditBirthday.setOnClickListener {
            DatePickerDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        DatePickerClickListener(
                            confirmActionWithDate = { date -> initDate(date) }
                        )
                    )
                }
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

    companion object {
        private const val SELECT_BIRTHDAY = "select birthday"
    }
}

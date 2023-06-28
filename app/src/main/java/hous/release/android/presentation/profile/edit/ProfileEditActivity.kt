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
import hous.release.android.util.extension.parcelable
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.android.util.extension.withArgs

@AndroidEntryPoint
class ProfileEditActivity :
    BindingActivity<ActivityProfileEditBinding>(R.layout.activity_profile_edit) {
    private val profileEditViewModel: ProfileEditViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = profileEditViewModel
        initProfileData()
        initProfileEditCollect()
        initBackBtnOnClickListener()
        initBirthdayOnClickListener()
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (profileEditViewModel.isProfileChanged.value) {
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

    private fun initBackBtnOnClickListener() {
        binding.btnProfileEditBack.setOnClickListener {
            if (profileEditViewModel.isProfileChanged.value) {
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
        val profileData = intent.parcelable<ProfileEntity>(PROFILE)
        profileEditViewModel.initData(requireNotNull(profileData))
    }

    private fun initProfileEditCollect() {
        repeatOnStarted {
            profileEditViewModel.isProfileEdit.collect { success ->
                if (success) {
                    finish()
                }
            }
        }
    }

    private fun initBirthdayOnClickListener() {
        binding.etProfileEditBirthday.setOnSingleClickListener {
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

    private fun initDate(date: String) {
        profileEditViewModel.initSelectedBirthDate(date)
    }

    companion object {
        private const val SELECT_BIRTHDAY = "select birthday"
    }
}

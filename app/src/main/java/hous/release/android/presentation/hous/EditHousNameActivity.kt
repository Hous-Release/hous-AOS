package hous.release.android.presentation.hous

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityEditHousNameBinding
import hous.release.android.presentation.hous.HousFragment.Companion.ROOM_NAME
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment.Companion.CONFIRM_ACTION
import hous.release.android.util.dialog.WarningDialogFragment.Companion.DIALOG_WARNING
import hous.release.android.util.dialog.WarningDialogFragment.Companion.WARNING_TYPE
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class EditHousNameActivity :
    BindingActivity<ActivityEditHousNameBinding>(R.layout.activity_edit_hous_name) {
    private val viewModel by viewModels<EditHousNameViewModel>()
    private val loadingDialog = LoadingDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
        initOriginalRoomName()
        initEditHousNameUiEventCollector()
    }

    private fun initEditTextClearFocus() {
        binding.layoutEditHousName.setOnClickListener {
            KeyBoardUtil.hide(this@EditHousNameActivity)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEditHousNameBack.setOnClickListener { exitEdit() }

        onBackPressedDispatcher.addCallback { exitEdit() }
    }

    private fun exitEdit() {
        if (viewModel.getIsEdited()) {
            showWarningDialog()
        } else {
            finish()
        }
    }

    private fun showWarningDialog() {
        WarningDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(WARNING_TYPE, WarningType.WARNING_EDIT_HOUS_NAME)
                putParcelable(
                    CONFIRM_ACTION,
                    ConfirmClickListener(confirmAction = { finish() })
                )
            }
        }.show(supportFragmentManager, DIALOG_WARNING)
    }

    private fun initOriginalRoomName() {
        viewModel.initOriginalRoomName(intent.getStringExtra(ROOM_NAME) ?: "")
    }

    private fun initEditHousNameUiEventCollector() {
        repeatOnStarted {
            viewModel.editHousNameUiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialog.show(supportFragmentManager, LoadingDialogFragment.TAG)
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialog.dismiss()
                        finish()
                    }
                    UiEvent.ERROR -> {
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }
}

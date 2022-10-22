package hous.release.android.presentation.hous

import android.os.Bundle
import hous.release.android.R
import hous.release.android.databinding.ActivityEditHousNameBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment.Companion.CONFIRM_ACTION
import hous.release.android.util.dialog.WarningDialogFragment.Companion.DIALOG_WARNING
import hous.release.android.util.dialog.WarningDialogFragment.Companion.WARNING_TYPE
import hous.release.android.util.dialog.WarningType

class EditHousNameActivity :
    BindingActivity<ActivityEditHousNameBinding>(R.layout.activity_edit_hous_name) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnEditHousNameBack.setOnClickListener {
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
    }
}

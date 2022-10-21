package hous.release.android.presentation.hous

import android.os.Bundle
import hous.release.android.R
import hous.release.android.databinding.ActivityEditHousNameBinding
import hous.release.android.util.binding.BindingActivity

class EditHousNameActivity :
        BindingActivity<ActivityEditHousNameBinding>(R.layout.activity_edit_hous_name) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnEditHousNameBack.setOnClickListener {
            WarningDialogFragment().show(supportFragmentManager, "경고")
        }
    }
}

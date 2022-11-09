package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import hous.release.android.R
import hous.release.android.databinding.ActivityWithdrawBinding
import hous.release.android.util.binding.BindingActivity

class WithdrawActivity :
    BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw),
    AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)
        initWithdrawFeedbackSpinner()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun initWithdrawFeedbackSpinner() {
        with(binding.spinnerWithdrawFeedbackSelect) {
            adapter = ArrayAdapter.createFromResource(
                this@WithdrawActivity,
                R.array.withdraw_feedback_items,
                android.R.layout.simple_spinner_item
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            onItemSelectedListener = this@WithdrawActivity
        }
    }
}

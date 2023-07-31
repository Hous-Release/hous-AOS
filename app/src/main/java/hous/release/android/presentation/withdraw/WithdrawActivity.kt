package hous.release.android.presentation.withdraw

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityWithdrawBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

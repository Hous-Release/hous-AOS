package hous.release.android.presentation.hous

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import hous.release.android.R
import hous.release.android.databinding.FragmentHousBinding
import hous.release.android.presentation.our_rules.OurRulesActivity
import hous.release.android.util.binding.BindingFragment

class HousFragment : BindingFragment<FragmentHousBinding>(R.layout.fragment_hous) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO 테스트해보고 지우기!!
        Log.d("로그", "HousFragment - onViewCreated() called")
        startActivity(Intent(context, OurRulesActivity::class.java))
    }
}

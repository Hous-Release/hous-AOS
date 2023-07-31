package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawDoneBinding
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class WithdrawDoneFragment :
    BindingFragment<FragmentWithdrawDoneBinding>(R.layout.fragment_withdraw_done) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onboarding.setOnClickListener {
            requireActivity().finish()
        }
    }
}
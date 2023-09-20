package hous.release.android.presentation.withdraw.withdrawDone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawDoneBinding
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.withdraw.WithdrawActivity.Companion.FEEDBACK
import hous.release.android.presentation.withdraw.WithdrawActivity.Companion.LOCATION
import hous.release.android.presentation.withdraw.WithdrawActivity.Companion.WITHDRAW
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class WithdrawDoneFragment :
    BindingFragment<FragmentWithdrawDoneBinding>(R.layout.fragment_withdraw_done) {
    private val withdrawDoneViewModel by viewModels<WithdrawDoneViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = withdrawDoneViewModel
        initLayout()
        initReturnBtnClickListener()
        initBackPressedCallback()
    }

    private fun initLayout() {
        arguments?.getString(LOCATION, "")?.let { location ->
            withdrawDoneViewModel.initLayout(location)
        }
    }

    private fun initReturnBtnClickListener() {
        binding.tvWithdrawDoneReturn.setOnSingleClickListener {
            when (withdrawDoneViewModel.layout.value) {
                WITHDRAW -> {
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    requireActivity().finishAffinity()
                }
                FEEDBACK -> {
                    requireActivity().finish()
                }
                else -> throw IllegalArgumentException(withdrawDoneViewModel.layout.value)
            }
        }
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            ActivityCompat.finishAffinity(requireActivity())
        }
    }
}

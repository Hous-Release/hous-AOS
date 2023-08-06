package hous.release.android.presentation.withdraw.withdrawDone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawDoneBinding
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class WithdrawDoneFragment :
    BindingFragment<FragmentWithdrawDoneBinding>(R.layout.fragment_withdraw_done) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReturnBtnClickListener()
        initBackPressedCallback()
    }

    private fun initReturnBtnClickListener() {
        binding.tvWithdrawDoneReturn.setOnSingleClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(Intent(requireActivity(), LoginActivity::class.java))
                    ActivityCompat.finishAffinity(requireActivity())
                }
            }
        )
    }
}

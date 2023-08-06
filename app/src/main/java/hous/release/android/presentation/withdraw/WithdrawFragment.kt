package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawBinding
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class WithdrawFragment : BindingFragment<FragmentWithdrawBinding>(R.layout.fragment_withdraw) {
    private val withdrawViewModel by viewModels<WithdrawViewModel>()
    private val loadingDialog = LoadingDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = withdrawViewModel
        collectUiEvent()
        initBackBtnClickListener()
    }

    /** TODO 영주 : 탈퇴하기 로직 어떻게 세워질지 보고 함수 살릴지 죽일지 결정 */
    private fun collectUiEvent() {
        repeatOnStarted {
            withdrawViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialog.show(childFragmentManager, LoadingDialogFragment.TAG)
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialog.dismiss()
                        findNavController().navigate(R.id.action_withdrawFragment_to_withdrawDoneFragment)
                    }
                    UiEvent.ERROR -> {
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawBack.setOnClickListener {
            requireActivity().finish()
        }
    }
}
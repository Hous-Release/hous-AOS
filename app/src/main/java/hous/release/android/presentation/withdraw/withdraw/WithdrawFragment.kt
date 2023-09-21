package hous.release.android.presentation.withdraw.withdraw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawBinding
import hous.release.android.presentation.withdraw.WithdrawActivity.Companion.LOCATION
import hous.release.android.presentation.withdraw.WithdrawActivity.Companion.WITHDRAW
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class WithdrawFragment : BindingFragment<FragmentWithdrawBinding>(R.layout.fragment_withdraw) {
    private val withdrawViewModel by viewModels<WithdrawViewModel>()
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = withdrawViewModel
        collectUiEvent()
        initBackBtnClickListener()
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            withdrawViewModel.uiEvent.collect { uiEvent ->
                val loadingDialogFragment =
                    childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment

                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialogFragment?.show(
                            childFragmentManager, LoadingDialogFragment.TAG
                        )
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialogFragment?.dismiss()
                        findNavController().navigate(R.id.action_withdrawFragment_to_withdrawDoneFragment,
                            bundle.apply {
                                putString(LOCATION, WITHDRAW)
                            }
                        )
                    }
                    UiEvent.ERROR -> {
                        loadingDialogFragment?.dismiss()
                    }
                }
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

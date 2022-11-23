package hous.release.android.presentation.our_rules.delete_rule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleDeleteBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesDeleteAdapter
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OurRuleDeleteFragment :
    BindingFragment<FragmentOurRuleDeleteBinding>(R.layout.fragment_our_rule_delete) {
    private val viewModel by viewModels<OurRuleDeleteViewModel>()
    private var ourRulesDeleteAdapter: OurRulesDeleteAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initAdapter()
        collectUiState()
        initClickListener()
    }

    private fun initClickListener() {
        binding.ivDeleteRuleBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivDeleteRuleButton.setOnClickListener {
            if (viewModel.isActiveDeleteButton()) showDeleteDialog()
        }
    }

    private fun deleteRules() {
        viewLifecycleOwner.lifecycleScope.launch {
            val loadingDialogFragment = LoadingDialogFragment()
            loadingDialogFragment.show(childFragmentManager, LoadingDialogFragment.TAG)
            viewModel.deleteRules().join()
            loadingDialogFragment.dismiss()
            findNavController().popBackStack()
        }
    }

    private fun showDeleteDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_DELETE_RULE
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = {
                    deleteRules()
                })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    private fun initAdapter() {
        ourRulesDeleteAdapter =
            OurRulesDeleteAdapter(viewModel::updateDeleteRules).also { adapter ->
                binding.rvDeleteOurRules.run {
                    this.adapter = adapter
                    itemAnimator = null
                }
            }
    }

    private fun collectUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                requireNotNull(ourRulesDeleteAdapter) {
                    getString(R.string.null_point_exception)
                }.submitList(
                    uiState.ruleList
                )
                if (viewModel.isDeleteRulesNotEmpty()) {
                    viewModel.setDeleteButtonState(ButtonState.ACTIVE)
                } else {
                    viewModel.setDeleteButtonState(ButtonState.INACTIVE)
                }
            }
        }
    }
}

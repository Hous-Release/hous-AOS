package hous.release.android.presentation.our_rules.add_rule

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleAddBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesAddAdapter
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OurRuleAddFragment :
    BindingFragment<FragmentOurRuleAddBinding>(R.layout.fragment_our_rule_add) {

    private val viewModel by viewModels<OurRuleAddViewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var ourRulesAddAdapter: OurRulesAddAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initBackButtonListener()
        initSaveButtonListener()
        initAddRuleButtonListener()
        initAdapter()
        collectUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        ourRulesAddAdapter = null
    }

    private fun initEditTextClearFocus() {
        binding.clAddRule.setOnClickListener {
            hideKeyBoard()
        }
    }

    private fun hideKeyBoard() = KeyBoardUtil.hide(requireActivity())

    private fun collectUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                requireNotNull(ourRulesAddAdapter) {
                    getString(R.string.null_point_exception)
                }.submitList(uiState.ourRuleList)
                if (viewModel.uiState.value.addedRuleList.isNotEmpty()) {
                    viewModel.setSaveButtonState(ButtonState.ACTIVE)
                } else {
                    viewModel.setSaveButtonState(ButtonState.INACTIVE)
                }
            }
        }
    }

    private fun initAdapter() {
        ourRulesAddAdapter = OurRulesAddAdapter(::hideKeyBoard).also { adapter ->
            binding.rvAddOurRules.run {
                this.adapter = adapter
                itemAnimator = null
            }
        }
    }

    private fun initAddRuleButtonListener() {
        binding.btnAddRule.setOnClickListener {
            if (viewModel.uiState.value.ourRuleList.size >= 30) {
                val errorDialogFragment = OurRuleAddErrorDialogFragment()
                errorDialogFragment.show(childFragmentManager, OUR_RULE_ADD_ERROR_DIALOG)
                return@setOnClickListener
            }
            viewModel.addRule()
        }
    }

    private fun initSaveButtonListener() {
        binding.ivAddRuleSaveButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val loadingDialogFragment = LoadingDialogFragment()
                loadingDialogFragment.show(childFragmentManager, LoadingDialogFragment.TAG)
                viewModel.putAddRuleList().join()
                loadingDialogFragment.dismiss()
                findNavController().popBackStack()
            }
        }
    }

    private fun initBackButtonListener() {
        requireActivity().onBackPressedDispatcher.addCallback {
            if (viewModel.isActiveSaveButton() || viewModel.inputRuleNameField.value.isNotBlank()) {
                showOutDialog()
                return@addCallback
            }
            findNavController().popBackStack()
        }.also { callback -> onBackPressedCallback = callback }

        binding.ivAddRuleBackButton.setOnClickListener {
            if (viewModel.isActiveSaveButton() || viewModel.inputRuleNameField.value.isNotBlank()) {
                showOutDialog()
                return@setOnClickListener
            }
            findNavController().popBackStack()
        }
    }

    private fun showOutDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_ADD_RULE
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { findNavController().popBackStack() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    companion object {
        const val OUR_RULE_ADD_ERROR_DIALOG = "our_rule_add_error_dialog"
    }
}

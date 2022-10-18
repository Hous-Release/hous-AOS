package hous.release.android.presentation.our_rules.add_rule

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleAddBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesAddAdapter
import hous.release.android.presentation.our_rules.type.SaveButtonState
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.safeLet
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OurRuleAddFragment :
    BindingFragment<FragmentOurRuleAddBinding>(R.layout.fragment_our_rule_add) {

    private val viewModel by viewModels<OurRuleAddViewModel>()
    private lateinit var ourRulesAddAdapter: OurRulesAddAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initBackButtonListener()
        initSaveButtonListener()
        initAddRuleButtonListener()
        initAdapter()
        observeUiState()
    }

    private fun observeUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                ourRulesAddAdapter.submitList(uiState.ourRuleList)
                if (viewModel.uiState.value.addedRuleList.isNotEmpty()) {
                    viewModel.setSaveButtonState(SaveButtonState.ACTIVE)
                } else {
                    viewModel.setSaveButtonState(SaveButtonState.INACTIVE)
                }
            }
        }
    }

    private fun initAdapter() {
        ourRulesAddAdapter = OurRulesAddAdapter()
        binding.rvAddOurRules.run {
            adapter = ourRulesAddAdapter
            itemAnimator = null
        }
    }

    private fun initAddRuleButtonListener() {
        binding.ivAddRuleBtn.setOnClickListener {
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
                viewModel.putAddRuleList().join()
                findNavController().popBackStack()
            }
        }
    }

    private fun initBackButtonListener() {
        safeLet(activity, activity?.onBackPressedDispatcher) { _, dispatcher ->
            dispatcher.addCallback {
                if (!this@OurRuleAddFragment.isAdded) {
                    this.remove()
                    dispatcher.onBackPressed()
                    return@addCallback
                }
                if (viewModel.uiState.value.saveButtonState == SaveButtonState.ACTIVE || viewModel.inputRuleNameField.value.isNotBlank()) {
                    val outDialogFragment = OurRuleAddOutDialogFragment()
                    outDialogFragment.show(childFragmentManager, OUR_RULE_ADD_OUT_DIALOG)
                    return@addCallback
                }
                findNavController().popBackStack()
            }
        } ?: Timber.e(
            getString(R.string.null_point_exception_detail_two_item).format(
                "activity",
                activity == null,
                "window",
                activity?.onBackPressedDispatcher == null
            )
        )

        binding.ivAddRuleBackButton.setOnClickListener {
            if (viewModel.uiState.value.saveButtonState == SaveButtonState.ACTIVE || viewModel.inputRuleNameField.value.isNotBlank()) {
                val outDialogFragment = OurRuleAddOutDialogFragment()
                outDialogFragment.show(childFragmentManager, OUR_RULE_ADD_OUT_DIALOG)
                return@setOnClickListener
            }
            findNavController().popBackStack()
        }
    }

    companion object {
        const val OUR_RULE_ADD_ERROR_DIALOG = "our_rule_add_error_dialog"
        const val OUR_RULE_ADD_OUT_DIALOG = "our_rule_add_out_dialog"
    }
}

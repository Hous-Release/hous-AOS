package hous.release.android.presentation.our_rules.edit_rule

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleEditBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesEditAdapter
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.util.ItemTouchHelperCallback
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OurRuleEditFragment :
    BindingFragment<FragmentOurRuleEditBinding>(R.layout.fragment_our_rule_edit) {
    private val viewModel by viewModels<OurRuleEditViewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
        initAdapter()
        collectUiState()
        initSaveButtonListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    private fun initEditTextClearFocus() {
        binding.clEditRule.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initBackBtnClickListener() {
        binding.ivEditRuleBackButton.setOnClickListener {
            if (viewModel.uiState.value.saveButtonState == ButtonState.ACTIVE) {
                showOutDialog()
                return@setOnClickListener
            }
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.apply {
            addCallback {
                if (viewModel.isActiveSaveButton()) {
                    showOutDialog()
                    return@addCallback
                }
                findNavController().popBackStack()
            }.also { callback ->
                onBackPressedCallback = callback
            }
        }
    }

    private fun showOutDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_EDIT_RULE
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { findNavController().popBackStack() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    private fun initAdapter() {
        val itemTouchHelper: ItemTouchHelper
        OurRulesEditAdapter(viewModel::updateEditRuleList)
            .also { adapter ->
                binding.rvEditOurRules.run {
                    this.adapter = adapter
                    itemAnimator = null
                }
            }.also { adapter ->
                itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter)).apply {
                    attachToRecyclerView(binding.rvEditOurRules)
                }
            }.also { adapter ->
                adapter.startDrag(object : OurRulesEditAdapter.OnStartDragListener {
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                        itemTouchHelper.startDrag(viewHolder)
                    }
                })
            }
    }

    private fun collectUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                requireNotNull(binding.rvEditOurRules.adapter as OurRulesEditAdapter) { getString(R.string.null_point_exception) }.submitList(
                    uiState.editRuleList
                )
                if (viewModel.isChangeRuleList()) {
                    viewModel.setSaveButtonState(ButtonState.ACTIVE)
                } else {
                    viewModel.setSaveButtonState(ButtonState.INACTIVE)
                }
            }
        }
    }

    private fun initSaveButtonListener() {
        binding.ivEditRuleSaveButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.putOurEditRules().join()
                findNavController().popBackStack()
            }
        }
    }
}

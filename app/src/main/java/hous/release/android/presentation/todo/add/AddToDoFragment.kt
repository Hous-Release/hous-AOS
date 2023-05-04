package hous.release.android.presentation.todo.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentAddToDoBinding
import hous.release.android.presentation.todo.detail.TodoLimitDialog
import hous.release.android.presentation.todo.edit.UpdateToDoEvent
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class AddToDoFragment : BindingFragment<FragmentAddToDoBinding>(R.layout.fragment_add_to_do) {
    private val viewModel by viewModels<AddToDoVIewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initToDoUserScreen()
        initBackButtonListener()
        collectTodoName()
        collectUiEvent()
        initEditTextClearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clAddToDo.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(requireActivity())
            return@setOnTouchListener false
        }
        binding.composeViewAddToDo.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun hideKeyBoard() {
        KeyBoardUtil.hide(requireActivity())
    }

    private fun initToDoUserScreen() {
        binding.composeViewAddToDo.setContent {
            HousTheme {
                AddTodoUserScreen(
                    viewModel = viewModel,
                    showLoadingDialog = ::showLoadingDialog,
                    name = getString(R.string.to_do_add_button),
                    hideKeyBoard = ::hideKeyBoard
                )
            }
        }
    }

    private fun showLoadingDialog() {
        LoadingDialogFragment().show(childFragmentManager, LoadingDialogFragment.TAG)
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoName.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            viewModel.uiEvent.collect { uiEvent ->
                (childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment)?.dismiss()
                when (uiEvent) {
                    UpdateToDoEvent.Finish -> {
                        findNavController().popBackStack()
                    }
                    UpdateToDoEvent.Limit -> {
                        TodoLimitDialog().show(childFragmentManager, TodoLimitDialog.TAG)
                    }
                    UpdateToDoEvent.Duplicate -> {
                        ToastMessageUtil.showToast(
                            requireContext(),
                            getString(R.string.to_do_duplicate_toast_msg)
                        )
                    }
                }
            }
        }
    }

    private fun initBackButtonListener() {
        requireActivity().onBackPressedDispatcher.addCallback {
            if (viewModel.isActiveAddButton() || !viewModel.isBlankToDoName()) {
                showOutDialog()
                return@addCallback
            }
            findNavController().popBackStack()
        }.also { callback -> onBackPressedCallback = callback }

        binding.btnAddToDoBack.setOnClickListener {
            if (viewModel.isActiveAddButton() || !viewModel.isBlankToDoName()) {
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
                WarningType.WARNING_ADD_TO_DO
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { findNavController().popBackStack() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }
}

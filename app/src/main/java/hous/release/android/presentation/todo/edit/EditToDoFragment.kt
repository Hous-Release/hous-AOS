package hous.release.android.presentation.todo.edit

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentEditToDoBinding
import hous.release.android.presentation.todo.detail.TodoLimitDialog
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
class EditToDoFragment : BindingFragment<FragmentEditToDoBinding>(R.layout.fragment_edit_to_do) {

    private val viewModel by viewModels<EditToDoViewModel>()
    private val safeArgs: EditToDoFragmentArgs by navArgs()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        passIdToViewModel()
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

    private fun passIdToViewModel() {
        val toDoId = safeArgs.todoId
        viewModel.fetchEditTodoContent(toDoId)
    }

    private fun initEditTextClearFocus() {
        binding.clEditToDo.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
        binding.composeViewEditToDo.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun hideKeyBoard() {
        KeyBoardUtil.hide(requireActivity())
    }

    private fun initToDoUserScreen() {
        binding.composeViewEditToDo.setContent {
            HousTheme {
                EditTodoUserScreen(
                    viewModel = viewModel,
                    showLoadingDialog = ::showLoadingDialog,
                    name = getString(R.string.to_do_save_button),
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
            if (viewModel.isChangeToDoName()) return@addCallback showOutDialog()
            findNavController().popBackStack()
        }.also { callback -> onBackPressedCallback = callback }

        binding.btnEditToDoBack.setOnClickListener {
            if (viewModel.isChangeToDoName()) return@setOnClickListener showOutDialog()
            findNavController().popBackStack()
        }
    }

    private fun showOutDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_EDIT_TO_DO
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { findNavController().popBackStack() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }
}

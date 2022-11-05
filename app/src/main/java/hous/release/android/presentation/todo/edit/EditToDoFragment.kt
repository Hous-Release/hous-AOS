package hous.release.android.presentation.todo.edit

import android.annotation.SuppressLint
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
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import hous.release.android.util.style.HousTheme

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

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clEditToDo.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(requireActivity())
            return@setOnTouchListener false
        }
        binding.composeViewEditToDo.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(requireActivity())
            return@setOnTouchListener false
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
                    finish = { findNavController().popBackStack() },
                    name = getString(R.string.to_do_add_button),
                    hideKeyBoard = ::hideKeyBoard
                )
            }
        }
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoName.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }

    private fun initBackButtonListener() {
        requireActivity().onBackPressedDispatcher.addCallback {
            showOutDialog()
        }.also { callback -> onBackPressedCallback = callback }

        binding.btnEditToDoBack.setOnClickListener {
            showOutDialog()
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

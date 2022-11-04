package hous.release.android.presentation.todo.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentAddToDoBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import hous.release.android.util.style.HousTheme

@AndroidEntryPoint
class AddToDoFragment : BindingFragment<FragmentAddToDoBinding>(R.layout.fragment_add_to_do) {
    private val viewModel by viewModels<AddToDoVIewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setStatusBarColor(colorRes = R.color.hous_white)
        initToDoUserScreen()
        initBackButtonListener()
        collectTodoName()
        initEditTextClearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        setStatusBarColor(colorRes = R.color.hous_g_1)
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
                    finish = { findNavController().popBackStack() },
                    name = getString(R.string.to_do_add_button),
                    hideKeyBoard = ::hideKeyBoard
                )
            }
        }
    }

    private fun setStatusBarColor(@ColorRes colorRes: Int) {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), colorRes)
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

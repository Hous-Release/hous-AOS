package hous.release.android.presentation.todo.add

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.extension.repeatOnStarted
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class AddToDoActivity : ComponentActivity() {
    private val viewModel by viewModels<AddToDoVIewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HousTheme {
                val todoText = viewModel.todoText.collectAsStateWithLifecycle()
                AddTodoUserScreen(
                    viewModel = viewModel,
                    showLoadingDialog = ::showLoadingDialog,
                    todoText = todoText.value,
                    setTodoText = viewModel::setTodoText,
                    hideKeyBoard = ::hideKeyBoard
                )
            }
        }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.vm = viewModel
//        initToDoUserScreen()
//        initBackButtonListener()
//        collectTodoName()
//        collectUiEvent()
//        initEditTextClearFocus()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        onBackPressedCallback.remove()
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun initEditTextClearFocus() {
//        binding.clAddToDo.setOnTouchListener { _, _ ->
//            KeyBoardUtil.hide(requireActivity())
//            return@setOnTouchListener false
//        }
//        binding.composeViewAddToDo.setOnTouchListener { _, _ ->
//            KeyBoardUtil.hide(requireActivity())
//            return@setOnTouchListener false
//        }
//    }

    private fun hideKeyBoard() {
        KeyBoardUtil.hide(this)
    }

    private fun showLoadingDialog() {
//        LoadingDialogFragment().show(this, LoadingDialogFragment.TAG)
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoText.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }
/*
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

//        binding.btnAddToDoBack.setOnClickListener {
//            if (viewModel.isActiveAddButton() || !viewModel.isBlankToDoName()) {
//                showOutDialog()
//                return@setOnClickListener
//            }
//            findNavController().popBackStack()
//        }
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
    }*/
}

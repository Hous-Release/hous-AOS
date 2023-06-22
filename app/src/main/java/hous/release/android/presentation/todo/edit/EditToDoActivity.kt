package hous.release.android.presentation.todo.edit

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
class EditToDoActivity : ComponentActivity() {

    private val viewModel by viewModels<EditToDoViewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        passIdToViewModel()
        setContent {
            val todoText = viewModel.todoText.collectAsStateWithLifecycle()
            HousTheme {
                EditTodoUserScreen(
                    viewModel = viewModel,
                    todoText = todoText.value,
                    setTodoText = viewModel::setTodoText,
                    showLoadingDialog = ::showLoadingDialog,
                    hideKeyBoard = ::hideKeyBoard
                )
            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.vm = viewModel
//        passIdToViewModel()
//        initBackButtonListener()
//        collectTodoName()
//        collectUiEvent()
//        initEditTextClearFocus()
//    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        onBackPressedCallback.remove()
//    }

    private fun passIdToViewModel() {
        val todoId = intent.getIntExtra("todoId", 0)
        viewModel.fetchEditTodoContent(todoId)
    }

    private fun initEditTextClearFocus() {
//        binding.clEditToDo.setOnClickListener {
//            KeyBoardUtil.hide(requireActivity())
//        }
//        binding.composeViewEditToDo.setOnClickListener {
//            KeyBoardUtil.hide(requireActivity())
//        }
    }

    private fun hideKeyBoard() {
        KeyBoardUtil.hide(this)
    }

    private fun showLoadingDialog() {
//        LoadingDialogFragment().show(childFragmentManager, LoadingDialogFragment.TAG)
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoText.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
//            viewModel.uiEvent.collect { uiEvent ->
//                (childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment)?.dismiss()
//                when (uiEvent) {
//                    UpdateToDoEvent.Finish -> {
//                        findNavController().popBackStack()
//                    }
//                    UpdateToDoEvent.Limit -> {
//                        TodoLimitDialog().show(childFragmentManager, TodoLimitDialog.TAG)
//                    }
//                    UpdateToDoEvent.Duplicate -> {
//                        ToastMessageUtil.showToast(
//                            requireContext(),
//                            getString(R.string.to_do_duplicate_toast_msg)
//                        )
//                    }
//                }
//            }
        }
    }

    private fun initBackButtonListener() {
//        requireActivity().onBackPressedDispatcher.addCallback {
//            if (viewModel.isChangeToDoName()) return@addCallback showOutDialog()
//            findNavController().popBackStack()
//        }.also { callback -> onBackPressedCallback = callback }
//
////        binding.btnEditToDoBack.setOnClickListener {
////            if (viewModel.isChangeToDoName()) return@setOnClickListener showOutDialog()
////            findNavController().popBackStack()
////        }
    }

    private fun showOutDialog() {
//        WarningDialogFragment().withArgs {
//            putSerializable(
//                WarningDialogFragment.WARNING_TYPE,
//                WarningType.WARNING_EDIT_TO_DO
//            )
//            putParcelable(
//                WarningDialogFragment.CONFIRM_ACTION,
//                ConfirmClickListener(confirmAction = { findNavController().popBackStack() })
//            )
//        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }
}

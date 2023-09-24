package hous.release.android.presentation.todo.add

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.presentation.todo.detail.TodoLimitDialog
import hous.release.android.presentation.todo.edit.UpdateToDoEvent
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.withArgs
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class AddToDoActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddToDoVIewModel>()

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
                    hideKeyBoard = ::hideKeyBoard,
                    checkFinish = ::checkFinish
                )
            }
        }
        initBackButtonListener()
        collectTodoName()
        collectUiEvent()
    }

    private fun hideKeyBoard() {
        KeyBoardUtil.hide(this)
    }

    private fun showLoadingDialog() {
        LoadingDialogFragment().show(supportFragmentManager, LoadingDialogFragment.TAG)
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoText.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }

    private fun checkFinish() {
        if (viewModel.isActiveAddButton() || !viewModel.isBlankToDoName()) {
            showOutDialog()
            return
        }
        finish()
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            viewModel.uiEvent.collect { uiEvent ->
                (supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment)?.dismiss()
                when (uiEvent) {
                    UpdateToDoEvent.Finish -> {
                        finish()
                    }
                    UpdateToDoEvent.Limit -> {
                        TodoLimitDialog().show(supportFragmentManager, TodoLimitDialog.TAG)
                    }
                    UpdateToDoEvent.Duplicate -> {
                        ToastMessageUtil.showToast(
                            this,
                            getString(R.string.to_do_duplicate_toast_msg)
                        )
                    }
                }
            }
        }
    }

    private fun initBackButtonListener() {
        onBackPressedDispatcher.addCallback {
            if (viewModel.isActiveAddButton() || !viewModel.isBlankToDoName()) {
                showOutDialog()
                return@addCallback
            }
            finish()
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
                ConfirmClickListener(confirmAction = { finish() })
            )
        }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }
}

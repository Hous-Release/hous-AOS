package hous.release.android.presentation.todo.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import hous.release.android.R
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.util.component.TodoUserScreen
import hous.release.android.util.extension.collectAsStateWithLifecycleRemember

@Composable
fun AddTodoUserScreen(
    viewModel: AddToDoVIewModel,
    todoText: String,
    setTodoText: (String) -> Unit,
    showLoadingDialog: () -> Unit = {},
    hideKeyBoard: () -> Unit,
    checkFinish: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycleRemember(UpdateToDoUiState())
    TodoUserScreen(
        title = stringResource(R.string.todo_add_title),
        todoText = todoText,
        buttonText = stringResource(id = R.string.to_do_save_button),
        setTodoText = setTodoText,
        uiState = uiState,
        checkUser = viewModel::checkUser,
        selectTodoDay = viewModel::selectTodoDay,
        showLoadingDialog = showLoadingDialog,
        putToDo = viewModel::putTodo,
        hideKeyBoard = hideKeyBoard,
        checkFinish = checkFinish,
        changeNotification = viewModel::changeNotificationState
    )
}

package hous.release.android.presentation.todo.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import hous.release.android.R
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.util.component.TodoUserScreen
import hous.release.android.util.extension.collectAsStateWithLifecycleRemember

@Composable
fun EditTodoUserScreen(
    viewModel: EditToDoViewModel,
    todoText: String,
    setTodoText: (String) -> Unit,
    checkFinish: () -> Unit,
    showLoadingDialog: () -> Unit,
    hideKeyBoard: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycleRemember(UpdateToDoUiState())
    TodoUserScreen(
        title = stringResource(R.string.todo_edit_title),
        todoText = todoText,
        buttonText = stringResource(id = R.string.to_do_save_button),
        setTodoText = setTodoText,
        uiState = uiState,
        checkUser = viewModel::checkUser,
        selectTodoDay = viewModel::selectTodoDay,
        checkFinish = checkFinish,
        showLoadingDialog = showLoadingDialog,
        putToDo = viewModel::putTodo,
        hideKeyBoard = hideKeyBoard
    )
}

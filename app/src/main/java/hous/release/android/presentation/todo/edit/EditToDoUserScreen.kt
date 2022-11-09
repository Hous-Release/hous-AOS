package hous.release.android.presentation.todo.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.util.component.TodoUserScreen
import hous.release.android.util.extension.collectAsStateWithLifecycleRemember

@Composable
fun EditTodoUserScreen(
    viewModel: EditToDoViewModel,
    finish: () -> Boolean,
    name: String,
    hideKeyBoard: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycleRemember(UpdateToDoUiState())
    TodoUserScreen(
        uiState = uiState,
        checkUser = viewModel::checkUser,
        selectTodoDay = viewModel::selectTodoDay,
        finish = finish,
        name = name,
        putToDo = viewModel::putTodo,
        hideKeyBoard = hideKeyBoard
    )
}

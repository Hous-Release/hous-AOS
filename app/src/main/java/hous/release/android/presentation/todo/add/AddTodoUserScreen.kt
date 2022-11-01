package hous.release.android.presentation.todo.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import hous.release.android.util.component.TodoUserScreen
import hous.release.android.util.extension.collectAsStateWithLifecycleRemember
import kotlinx.coroutines.Job

@Composable
fun AddTodoUserScreen(
    viewModel: AddToDoVIewModel,
    finish: () -> Boolean,
    name: String,
    putToDo: () -> Job
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycleRemember(AddToDoVIewModel.AddToDoUiState())
    TodoUserScreen(
        uiState = uiState,
        checkUser = viewModel::checkUser,
        selectTodoDay = viewModel::selectTodoDay,
        finish = finish,
        name = name,
        putToDo = putToDo
    )
}

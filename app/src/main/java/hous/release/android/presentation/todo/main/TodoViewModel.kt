package hous.release.android.presentation.todo.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.todo.main.TodoState.IDLE
import hous.release.android.presentation.todo.main.TodoState.PROGRESS
import hous.release.domain.entity.Todo
import hous.release.domain.repository.TodoRepository
import hous.release.domain.usecase.todo.GetIsAddableTodoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val getIsAddableTodoUseCase: GetIsAddableTodoUseCase
) : ViewModel() {
    private var todoState = IDLE
    private val _todoEvent = MutableSharedFlow<TodoEvent>()
    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState = _uiState.asStateFlow()
    val todoEvent = _todoEvent.asSharedFlow()

    fun fetchTodoMainContent() {
        viewModelScope.launch {
            todoRepository.getTodoMainContent()
                .onSuccess { result ->
                    _uiState.value = ToDoUiState(
                        date = result.date,
                        dayOfWeek = result.dayOfWeek,
                        myTodos = result.myTodos,
                        ourTodos = result.ourTodos,
                        myTodosCount = result.myTodosCnt,
                        ourTodosCount = result.ourTodosCnt,
                        progress = (result.progress) / 100f
                    )
                }
                .onFailure { Timber.d("error: ${it.message}") }
        }
    }

    fun checkTodo(todoId: Int, isChecked: Boolean) {
        if (todoState == IDLE) {
            todoState = PROGRESS
            viewModelScope.launch {
                todoRepository.checkTodo(todoId = todoId, isChecked = isChecked)
                    .onSuccess {
                        fetchTodoMainContent()
                        todoState = IDLE
                    }
                    .onFailure {
                        Timber.e("error : ${it.message}")
                        todoState = IDLE
                    }
            }
        }
    }

    fun getIsAddableTodo() {
        viewModelScope.launch {
            getIsAddableTodoUseCase()
                .onSuccess { isAddable ->
                    if (isAddable) _todoEvent.emit(TodoEvent.ADD_TODO)
                    else _todoEvent.emit(TodoEvent.SHOW_LIMIT_DIALOG)
                }
                .onFailure {
                    Timber.e("addable ${it.message}")
                    _todoEvent.emit(TodoEvent.SHOW_TOAST)
                }
        }
    }
}

data class ToDoUiState(
    val date: String = "",
    val dayOfWeek: String = "",
    val myTodos: List<Todo> = emptyList(),
    val myTodosCount: Int = 0,
    val ourTodos: List<Todo> = emptyList(),
    val ourTodosCount: Int = 0,
    val progress: Float = 0f
)

enum class TodoEvent {
    ADD_TODO, SHOW_LIMIT_DIALOG, SHOW_TOAST
}

enum class TodoState {
    IDLE, PROGRESS
}

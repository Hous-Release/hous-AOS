package hous.release.android.presentation.todo.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.ToDo
import hous.release.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchToDoMainContent()
        }
    }

    suspend fun fetchToDoMainContent() {
        toDoRepository.getToDoMainContent()
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

    fun checkTodo(todoId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            toDoRepository.checkToDo(todoId = todoId, isChecked = isChecked)
            fetchToDoMainContent()
        }
    }
}

data class ToDoUiState(
    val date: String = "",
    val dayOfWeek: String = "",
    val myTodos: List<ToDo> = emptyList(),
    val myTodosCount: Int = 0,
    val ourTodos: List<ToDo> = emptyList(),
    val ourTodosCount: Int = 0,
    val progress: Float = 0f
)

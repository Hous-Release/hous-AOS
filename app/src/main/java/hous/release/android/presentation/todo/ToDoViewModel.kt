package hous.release.android.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.ToDo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchToDoMainContent()
        }
    }

    suspend fun fetchToDoMainContent() {
        val tempUiState = ToDoUiState(
            date = "09.12",
            dayOfWeek = "Sunday",
            myTodos = listOf(
                ToDo(
                    isChecked = false,
                    todoId = 1,
                    todoName = "하우스"
                ),
                ToDo(
                    isChecked = false,
                    todoId = 2,
                    todoName = "릴리즈"
                ),
                ToDo(
                    isChecked = true,
                    todoId = 3,
                    todoName = "10월 말"
                ),
                ToDo(
                    isChecked = false,
                    todoId = 4,
                    todoName = "하우스"
                ),
                ToDo(
                    isChecked = false,
                    todoId = 5,
                    todoName = "릴리즈"
                ),
                ToDo(
                    isChecked = true,
                    todoId = 6,
                    todoName = "10월 말"
                ),
                ToDo(
                    isChecked = false,
                    todoId = 7,
                    todoName = "하우스"
                ),
                ToDo(
                    isChecked = false,
                    todoId = 8,
                    todoName = "릴리즈"
                ),
                ToDo(
                    isChecked = true,
                    todoId = 9,
                    todoName = "10월 말"
                )
            ),
            myTodosCount = 10,
            ourTodos = listOf(
                ToDo(
                    nicknames = listOf("강원용"),
                    status = "EMPTY",
                    todoName = "내일"
                ),
                ToDo(
                    nicknames = listOf("강원용", "이준원"),
                    status = "FULL",
                    todoName = "개강"
                ),
                ToDo(
                    nicknames = listOf("강원용", "이준원", "손연주", "이영주"),
                    status = "FULL_CHECK",
                    todoName = "실화"
                )
            ),
            ourTodosCount = 3,
            progress = 0.4f
        )
        delay(300)
        _uiState.value = tempUiState
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

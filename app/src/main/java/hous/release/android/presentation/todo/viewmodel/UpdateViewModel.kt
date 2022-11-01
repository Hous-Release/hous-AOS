package hous.release.android.presentation.todo.viewmodel

import androidx.lifecycle.ViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.entity.response.ToDoUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class UpdateToDoViewModel : ViewModel() {
    val todoName = MutableStateFlow("")
    protected var _uiState = MutableStateFlow(UpdateToDoUiState())
    val uiState = _uiState.asStateFlow()

    fun checkUser(userIdx: Int) {
        _uiState.update { uiState ->
            val newToDoUsers = uiState.todoUsers.mapIndexed { idx, toDoUser ->
                if (idx == userIdx) {
                    if (toDoUser.isChecked) {
                        return@mapIndexed toDoUser.copy(
                            isChecked = false,
                            dayOfWeeks = List(7) { false }
                        )
                    } else {
                        return@mapIndexed toDoUser.copy(isChecked = true)
                    }
                }
                toDoUser
            }
            uiState.copy(
                todoUsers = newToDoUsers,
                selectedUsers = newToDoUsers.filter { it.isChecked }
            )
        }
    }

    fun selectTodoDay(userIdx: Int, dayIdx: Int) {
        val dayOfWeeks: List<Boolean> =
            uiState.value.todoUsers[userIdx].dayOfWeeks.mapIndexed { idx, checkState ->
                if (idx == dayIdx) {
                    return@mapIndexed !checkState
                }
                checkState
            }
        _uiState.update { uiState ->
            val newToDoUsers = uiState.todoUsers.mapIndexed { idx, toDoUser ->
                if (idx == userIdx) {
                    return@mapIndexed uiState.todoUsers[idx].copy(
                        dayOfWeeks = dayOfWeeks
                    )
                }
                toDoUser
            }
            uiState.copy(
                todoUsers = newToDoUsers,
                selectedUsers = newToDoUsers.filter { it.isChecked }
            )
        }
    }

    fun changeNotificationState() {
        _uiState.update { uiState ->
            uiState.copy(
                isPushNotification = !uiState.isPushNotification
            )
        }
    }

    fun isActiveAddButton() = (uiState.value.buttonState == ButtonState.ACTIVE)
    fun isBlankToDoName(): Boolean = todoName.value.isBlank()
    fun setToDoNameState(isBlank: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(isBlankTodoName = isBlank)
        }
    }
}

data class UpdateToDoUiState(
    val selectedUsers: List<ToDoUser> = emptyList(),
    val todoUsers: List<ToDoUser> = emptyList(),
    val isPushNotification: Boolean = false,
    val buttonState: ButtonState = ButtonState.INACTIVE,
    val isBlankTodoName: Boolean = true
)

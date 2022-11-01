package hous.release.android.presentation.todo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToDoVIewModel @Inject constructor() : ViewModel() {
    val todoName = MutableStateFlow("")
    private var _uiState = MutableStateFlow(dummyUiState)
    val uiState = _uiState.asStateFlow()

    fun putTodo() = viewModelScope.launch {
        delay(500L)
        // TODO 서버 통신
    }

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
        Timber.e("${userIdx}")
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

    fun isBlankToDoName(): Boolean = todoName.value.isBlank()
    fun setToDoNameState(isBlank: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(isBlankTodoName = isBlank)
        }
    }

    data class AddToDoUiState(
        val selectedUsers: List<ToDoUser> = emptyList(),
        val todoUsers: List<ToDoUser> = emptyList(),
        val isPushNotification: Boolean = false,
        val buttonState: ButtonState = ButtonState.INACTIVE,
        val isBlankTodoName: Boolean = true
    )

    companion object {
        private val dummyUiState = AddToDoUiState(
            todoUsers = listOf(
                ToDoUser().copy(
                    homyType = HomyType.GREEN,
                    nickname = "이준원",
                    onBoardingId = 0
                ),
                ToDoUser().copy(
                    homyType = HomyType.RED,
                    nickname = "손연주",
                    onBoardingId = 1
                ),
                ToDoUser().copy(
                    homyType = HomyType.YELLOW,
                    nickname = "이영주",
                    onBoardingId = 2
                ),
                ToDoUser().copy(
                    homyType = HomyType.BLUE,
                    nickname = "강원용",
                    onBoardingId = 3
                )
            )
        )
    }
}

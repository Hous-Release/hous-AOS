package hous.release.android.presentation.todo.add

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToDoVIewModel @Inject constructor() : ViewModel() {
    val todoName = MutableStateFlow("")
    private var _uiState = MutableStateFlow(dummyUiState)
    val uiState = _uiState.asStateFlow()

    fun checkUser(userIdx: Int) {
        _uiState.update { uiState ->
            val newTodoUser = uiState.todoUsers.mapIndexed { idx, toDoUser ->
                if (idx == userIdx) {
                    if (toDoUser.isChecked) {
                        return@mapIndexed toDoUser.copy(
                            isChecked = !toDoUser.isChecked,
                            dayOfWeeks = List(7) { false }
                        )
                    } else {
                        return@mapIndexed toDoUser.copy(isChecked = !toDoUser.isChecked)
                    }
                }
                toDoUser
            }
            uiState.copy(
                todoUsers = newTodoUser,
                selectedUsers = newTodoUser.filter { it.isChecked }
            )
        }
        Timber.i(uiState.value.selectedUsers.map { it.nickname }.toString())
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
            uiState.copy(
                todoUsers =
                uiState.todoUsers.mapIndexed { idx, toDoUser ->
                    if (idx == userIdx) {
                        return@mapIndexed uiState.todoUsers[idx].copy(
                            dayOfWeeks = dayOfWeeks
                        )
                    }
                    toDoUser
                }
            )
        }
    }

    data class AddToDoUiState(
        val selectedUsers: List<ToDoUser> = emptyList(),
        val todoUsers: List<ToDoUser> = emptyList(),
        val isPushNotification: Boolean = false
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

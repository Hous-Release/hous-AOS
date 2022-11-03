package hous.release.android.presentation.todo.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.presentation.todo.viewmodel.UpdateToDoViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToDoVIewModel @Inject constructor() : UpdateToDoViewModel() {

    init {
        // TODO 추후 서버 통신
        _uiState.value = dummyUiState
    }

    fun putTodo() = viewModelScope.launch {
        // TODO 추후 서버 통신
        delay(500L)
    }

    companion object {
        private val dummyUiState = UpdateToDoUiState(
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

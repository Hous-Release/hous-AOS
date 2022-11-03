package hous.release.android.presentation.todo.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.todo.viewmodel.UpdateToDoUiState
import hous.release.android.presentation.todo.viewmodel.UpdateToDoViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.usecase.GetToDoUsersUseCase
import hous.release.domain.usecase.PostAddToDoUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToDoVIewModel @Inject constructor(
    private val getToDoUsersUseCase: GetToDoUsersUseCase,
    private val postAddToDoUseCase: PostAddToDoUseCase
) : UpdateToDoViewModel() {

    init {
        viewModelScope.launch {
            getToDoUsersUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> _uiState.update { uiState ->
                        uiState.copy(todoUsers = apiResult.data)
                    }
                    is ApiResult.Error -> Timber.e(apiResult.msg)
                    is ApiResult.Empty -> Timber.i("empty View")
                }
            }
        }
    }

    fun putTodo() = viewModelScope.launch {
        postAddToDoUseCase(
            isPushNotification = uiState.value.isPushNotification,
            todoUsers = uiState.value.selectedUsers,
            toDoName = todoName.value
        ).collect { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> Timber.i(apiResult.data)
                is ApiResult.Error -> Timber.e(apiResult.msg)
                is ApiResult.Empty -> Timber.e(IllegalArgumentException())
            }
        }
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

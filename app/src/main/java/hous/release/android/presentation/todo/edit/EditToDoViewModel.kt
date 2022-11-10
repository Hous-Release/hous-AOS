package hous.release.android.presentation.todo.edit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.presentation.todo.viewmodel.UpdateToDoViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.usecase.GetEditTodoContentUseCase
import hous.release.domain.usecase.PutEditToDoUseCase
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditToDoViewModel @Inject constructor(
    private val getEditTodoContentUseCase: GetEditTodoContentUseCase,
    private val putEditToDoUseCase: PutEditToDoUseCase
) :
    UpdateToDoViewModel() {
    private var todoId = -1
    override fun putTodo() = viewModelScope.launch {
        putEditToDoUseCase(
            todoId = todoId,
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

    fun fetchEditTodoContent(todoId: Int) {
        this.todoId = todoId
        viewModelScope.launch {
            getEditTodoContentUseCase(todoId).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> _uiState.update { uiState ->
                        val data = apiResult.data
                        todoName.value = data.name
                        uiState.copy(
                            isPushNotification = data.isPushNotification,
                            buttonState = ButtonState.ACTIVE,
                            isBlankTodoName = false,
                            todoUsers = data.todoUsers,
                            selectedUsers = data.todoUsers.filter { it.isChecked }
                        )
                    }
                    is ApiResult.Error -> Timber.e(apiResult.msg)
                    is ApiResult.Empty -> Timber.i("empty View")
                }
            }
        }
    }
}

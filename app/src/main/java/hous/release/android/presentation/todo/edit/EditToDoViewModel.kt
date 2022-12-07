package hous.release.android.presentation.todo.edit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.presentation.todo.viewmodel.UpdateToDoViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.usecase.GetEditTodoContentUseCase
import hous.release.domain.usecase.PutEditToDoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _todoHint = MutableStateFlow("")
    val todoHint = _todoHint.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UpdateToDoEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    override fun putTodo() = viewModelScope.launch {
        putEditToDoUseCase(
            todoId = todoId,
            isPushNotification = uiState.value.isPushNotification,
            todoUsers = uiState.value.selectedUsers,
            toDoName = todoName.value
        ).collect { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    Timber.i(apiResult.data)
                    _uiEvent.emit(UpdateToDoEvent.Finish)
                }
                is ApiResult.Error -> {
                    Timber.e(apiResult.msg)
                    when (apiResult.msg) {
                        DUPLICATED_ERROR_CODE -> _uiEvent.emit(UpdateToDoEvent.Duplicate)
                        LIMITED_ERROR_CODE -> _uiEvent.emit(UpdateToDoEvent.Limit)
                    }
                }
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
                        _todoHint.update { data.name }
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

    fun isChangeToDoName() = (todoHint.value == todoName.value)

    companion object {
        const val DUPLICATED_ERROR_CODE = "409"
        const val LIMITED_ERROR_CODE = "403"
    }
}

sealed class UpdateToDoEvent {
    object Duplicate : UpdateToDoEvent()
    object Limit : UpdateToDoEvent()
    object Finish : UpdateToDoEvent()
}

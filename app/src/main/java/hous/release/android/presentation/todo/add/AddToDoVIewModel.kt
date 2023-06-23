package hous.release.android.presentation.todo.add

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.todo.edit.EditToDoViewModel
import hous.release.android.presentation.todo.edit.UpdateToDoEvent
import hous.release.android.presentation.todo.viewmodel.UpdateToDoViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.usecase.GetToDoUsersUseCase
import hous.release.domain.usecase.PostAddToDoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddToDoVIewModel @Inject constructor(
    private val getToDoUsersUseCase: GetToDoUsersUseCase,
    private val postAddToDoUseCase: PostAddToDoUseCase
) : UpdateToDoViewModel() {

    private val _uiEvent = MutableSharedFlow<UpdateToDoEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

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

    override fun putTodo() = viewModelScope.launch {
        postAddToDoUseCase(
            isPushNotification = uiState.value.isPushNotification,
            todoUsers = uiState.value.selectedUsers,
            toDoName = todoText.value
        ).collect { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    Timber.i(apiResult.data)
                    _uiEvent.emit(UpdateToDoEvent.Finish)
                }
                is ApiResult.Error -> {
                    Timber.e(apiResult.msg)
                    when (apiResult.msg) {
                        EditToDoViewModel.DUPLICATED_ERROR_CODE -> _uiEvent.emit(UpdateToDoEvent.Duplicate)
                        EditToDoViewModel.LIMITED_ERROR_CODE -> _uiEvent.emit(UpdateToDoEvent.Limit)
                    }
                }
                is ApiResult.Empty -> Timber.e(IllegalArgumentException())
            }
        }
    }
}

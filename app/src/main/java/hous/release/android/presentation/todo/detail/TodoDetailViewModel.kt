package hous.release.android.presentation.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.usecase.DeleteTodoUseCase
import hous.release.domain.usecase.GetDailyTodosUseCase
import hous.release.domain.usecase.GetMemberTodosUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getMemberTodosUseCase: GetMemberTodosUseCase,
    private val dailyTodosUseCase: GetDailyTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _isFinish = MutableSharedFlow<Boolean>()
    val isFinish = _isFinish.asSharedFlow()

    init {
        fetchMemberToDos()
        fetchDailyToDos()
    }

    fun fetchMemberToDos() {
        viewModelScope.launch {
            getMemberTodosUseCase()
                .onSuccess { result ->
                    _uiState.value = _uiState.value.copy(
                        memberToDos = result.todos,
                        totalTodoCount = result.totalRoomTodoCnt
                    )
                }
                .onFailure {
                    Timber.e("error: ${it.message}")
                }
        }
    }

    fun fetchDailyToDos() {
        viewModelScope.launch {
            dailyTodosUseCase()
                .onSuccess { result ->
                    _uiState.value = _uiState.value.copy(dailyTodos = result)
                    fetchMemberToDos()
                }
                .onFailure {
                    Timber.e("error: ${it.message}")
                }
        }
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId)
            fetchMemberToDos()
            fetchDailyToDos()
        }
    }

    fun setMemberTabIndex(index: Int) {
        _uiState.value = _uiState.value.copy(memberTabIndex = index)
    }

    fun setDailyTabIndex(index: Int) {
        _uiState.value = _uiState.value.copy(dailyTabIndex = index)
    }

    fun setIsFinish() {
        viewModelScope.launch { _isFinish.emit(true) }
    }
}

data class TodoDetailUiState(
    val memberToDos: List<MemberTodoContent> = emptyList(),
    val dailyTodos: List<TodoMain> = emptyList(),
    val memberTabIndex: Int = 0,
    val dailyTabIndex: Int = 0,
    val totalTodoCount: Int = 0
)

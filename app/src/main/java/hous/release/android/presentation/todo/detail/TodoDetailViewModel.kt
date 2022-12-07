package hous.release.android.presentation.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.todo.main.TodoState
import hous.release.android.presentation.todo.main.TodoState.IDLE
import hous.release.android.presentation.todo.main.TodoState.PROGRESS
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.usecase.DeleteTodoUseCase
import hous.release.domain.usecase.GetDailyTodosUseCase
import hous.release.domain.usecase.GetMemberTodosUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getMemberTodosUseCase: GetMemberTodosUseCase,
    private val dailyTodosUseCase: GetDailyTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableSharedFlow<TodoState>()
    val isLoading = _isLoading.asSharedFlow()

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
                    _uiState.value = _uiState.value.copy(
                        dailyTodos = result.todos,
                        totalTodoCount = result.totalRoomTodoCnt
                    )
                }
                .onFailure {
                    Timber.e("error: ${it.message}")
                }
        }
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            _isLoading.emit(PROGRESS)
            deleteTodoUseCase(todoId)
                .onSuccess {
                    _isLoading.emit(IDLE)
                    fetchMemberToDos()
                    fetchDailyToDos()
                }
                .onFailure {
                    _isLoading.emit(IDLE)
                    Timber.e("delete error ${it.message}")
                }
        }
    }

    fun setMemberTabIndex(index: Int) {
        _uiState.value = _uiState.value.copy(memberTabIndex = index)
    }

    fun setDailyTabIndex(index: Int) {
        _uiState.value = _uiState.value.copy(dailyTabIndex = index)
    }
}

data class TodoDetailUiState(
    val memberToDos: List<MemberTodoContent> = emptyList(),
    val dailyTodos: List<TodoMain> = emptyList(),
    val memberTabIndex: Int = 0,
    val dailyTabIndex: Int = 0,
    val totalTodoCount: Int = 0
)

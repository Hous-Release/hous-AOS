package hous.release.android.presentation.todo.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.usecase.DeleteTodoUseCase
import hous.release.domain.usecase.GetMemberTodosUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val getMemberTodosUseCase: GetMemberTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _memberTodoUiState: MutableStateFlow<MemberTodoUiState> =
        MutableStateFlow(
            MemberTodoUiState()
        )
    val memberTodoUiState = _memberTodoUiState.asStateFlow()

    init {
        fetchMemberToDos()
    }

    private fun fetchMemberToDos() {
        viewModelScope.launch {
            getMemberTodosUseCase()
                .onSuccess { result ->
                    _memberTodoUiState.value = _memberTodoUiState.value.copy(memberToDos = result)
                }
                .onFailure {
                    Timber.e("error: ${it.message}")
                }
        }
    }

    fun setTabCurrIndex(index: Int) {
        _memberTodoUiState.value = _memberTodoUiState.value.copy(memberTabCurrIndex = index)
    }

    fun deleteTodo(todoId: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId)
            fetchMemberToDos()
        }
    }
}

data class MemberTodoUiState(
    val memberToDos: List<MemberTodoContent> = emptyList(),
    val memberTabCurrIndex: Int = 0
)

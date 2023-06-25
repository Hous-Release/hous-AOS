package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.todo.FilteredTodo
import hous.release.domain.usecase.DeleteTodoUseCase
import hous.release.domain.usecase.search.SearchRuleUseCase
import hous.release.domain.usecase.todo.GetFilteredTodoUseCase
import hous.release.domain.usecase.todo.GetIsAddableTodoUseCase
import hous.release.domain.usecase.todo.GetToDoUsersUseCase
import hous.release.domain.usecase.todo.GetTodoDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getFilteredTodoUseCase: GetFilteredTodoUseCase,
    private val searchRuleUseCase: SearchRuleUseCase,
    private val getIsAddableTodoUseCase: GetIsAddableTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getTodoDetailUseCase: GetTodoDetailUseCase,
    private val getToDoUsersUseCase: GetToDoUsersUseCase
) : ViewModel() {
    private val _todoEvent = MutableSharedFlow<TodoEvent>()
    private val _searchText = MutableStateFlow("")
    private val _selectedDayOfWeeks: MutableStateFlow<List<SelectableDayOfWeek>> =
        MutableStateFlow(emptyList())
    private val _homies: MutableStateFlow<List<SelectableHomy>> = MutableStateFlow(emptyList())
    private val _filteredTodo: MutableStateFlow<FilteredTodo> =
        MutableStateFlow(
            FilteredTodo(
                emptyList(),
                0
            )
        )
    private val _todoDetail: MutableStateFlow<TodoDetail> = MutableStateFlow(
        TodoDetail(
            name = "",
            selectedUsers = emptyList(),
            dayOfWeeks = ""
        )
    )

    val todoEvent = _todoEvent.asSharedFlow()
    val searchText: StateFlow<String> = _searchText.asStateFlow()
    val selectableWeek: StateFlow<List<SelectableDayOfWeek>> = _selectedDayOfWeeks.asStateFlow()
    val homies = _homies.asStateFlow()
    val filteredTodo = _filteredTodo.asStateFlow()
    val todoDetail = _todoDetail.asStateFlow()
    val selectedDayOfWeeks: StateFlow<String> =
        _selectedDayOfWeeks.map { selectedDays -> transformSelectedDaysToString(selectedDays) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ""
            )
    val selectedHomies: StateFlow<String> =
        _homies.map { homies -> transformSelectedHomiesToString(homies) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ""
            )

    init {
        setHomies()
        setSelectableWeek()
    }

    private fun transformSelectedDaysToString(selectedDays: List<SelectableDayOfWeek>): String {
        val selectedDayOfWeeks = selectedDays.filter { dayOfWeek -> dayOfWeek.isSelected }
        return if (selectedDayOfWeeks.count() != 7) selectedDayOfWeeks.joinToString(", ") { week -> week.dayOfWeek }
        else "매일"
    }

    private fun transformSelectedHomiesToString(homies: List<SelectableHomy>): String {
        val selectedHomies = homies.filter { homy -> homy.isSelected }
        return if (selectedHomies.count() > 1) "${selectedHomies[0].name} 외 ${selectedHomies.count() - 1}명"
        else selectedHomies.joinToString("") { homy -> homy.name }
    }

    fun fetchFilteredTodo() {
        viewModelScope.launch {
            val dayOfWeeks: List<String>? =
                selectableWeek.value
                    .filter { selectableDayOfWeek -> selectableDayOfWeek.isSelected }
                    .map { selectableDayOfWeek -> selectableDayOfWeek.dayOfWeek }
                    .nullIfEmpty()
            val onboardingIds: List<Int>? =
                homies.value
                    .filter { homy -> homy.isSelected }
                    .map { homy -> homy.id }
                    .nullIfEmpty()

            getFilteredTodoUseCase(dayOfWeeks, onboardingIds)
                .onSuccess { filteredTodo ->
                    _filteredTodo.value = if (searchText.value.isNotEmpty()) filteredTodo.copy(
                        todos = searchRuleUseCase(
                            searchText.value,
                            filteredTodo.todos
                        )
                    ) else filteredTodo
                }
                .onFailure {
                    Timber.e(it.message)
                }
        }
    }

    private fun setSelectableWeek() {
        _selectedDayOfWeeks.value =
            WEEK.map { dayOfWeek -> SelectableDayOfWeek(dayOfWeek = dayOfWeek) }
    }

    private fun setHomies() {
        viewModelScope.launch {
            getToDoUsersUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> _homies.value = apiResult.data.map { homy ->
                        SelectableHomy(
                            id = homy.onBoardingId,
                            name = homy.nickname,
                            homyType = homy.homyType
                        )
                    }
                    is ApiResult.Error -> Timber.e(apiResult.msg)
                    is ApiResult.Empty -> Timber.i("empty View")
                }
            }
        }
    }

    fun selectDayOfWeek(index: Int) {
        _selectedDayOfWeeks.value =
            _selectedDayOfWeeks.value.mapIndexed { idx, selectableDayOfWeek ->
                if (idx == index) selectableDayOfWeek.copy(isSelected = !selectableDayOfWeek.isSelected)
                else selectableDayOfWeek
            }
    }

    fun selectHomy(index: Int) {
        _homies.value = _homies.value.mapIndexed { idx, selectableHomy ->
            if (idx == index) selectableHomy.copy(isSelected = !selectableHomy.isSelected)
            else selectableHomy
        }
    }

    fun writeSearchText(text: String) {
        _searchText.value = text
        _filteredTodo.value = _filteredTodo.value
            .copy(
                todos = searchRuleUseCase(
                    searchText.value,
                    filteredTodo.value.todos
                )
            )
    }

    fun setTodoDetail(todoId: Int) {
        viewModelScope.launch {
            getTodoDetailUseCase(todoId)
                .onSuccess { todoDetail ->
                    _todoDetail.value = todoDetail
                }
                .onFailure {
                    Timber.e("delete error ${it.message}")
                }
        }
    }

    fun deleteTodo() {
        viewModelScope.launch {
            deleteTodoUseCase(todoDetail.value.todoId)
                .onSuccess {

                }
                .onFailure {
                    Timber.e("delete error ${it.message}")
                }
        }
    }

    fun getIsAddableTodo() {
        viewModelScope.launch {
            getIsAddableTodoUseCase()
                .onSuccess { isAddable ->
                    if (isAddable) _todoEvent.emit(TodoEvent.ADD_TODO)
                    else _todoEvent.emit(TodoEvent.SHOW_LIMIT_DIALOG)
                }
                .onFailure {
                    Timber.e("addable ${it.message}")
                    _todoEvent.emit(TodoEvent.SHOW_TOAST)
                }
        }
    }

    companion object {
        private val WEEK = listOf("월", "화", "수", "목", "금", "토", "일")
    }
}

enum class TodoEvent {
    ADD_TODO, SHOW_LIMIT_DIALOG, SHOW_TOAST
}

data class SelectableDayOfWeek(
    val dayOfWeek: String,
    val isSelected: Boolean = false
)

data class SelectableHomy(
    val id: Int,
    val isSelected: Boolean = false,
    val name: String,
    val homyType: HomyType
)

fun <T> List<T>.nullIfEmpty(): List<T>? {
    return ifEmpty { null }
}

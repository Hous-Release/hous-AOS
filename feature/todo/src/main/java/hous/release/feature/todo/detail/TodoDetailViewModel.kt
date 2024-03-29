package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.util.ApiResult
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.todo.FilteredTodo
import hous.release.domain.entity.todo.TodoWithNew
import hous.release.domain.usecase.DeleteTodoUseCase
import hous.release.domain.usecase.search.SearchRuleUseCase
import hous.release.domain.usecase.todo.GetFilteredTodoUseCase
import hous.release.domain.usecase.todo.GetIsAddableTodoUseCase
import hous.release.domain.usecase.todo.GetToDoUsersUseCase
import hous.release.domain.usecase.todo.GetTodoDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val originalTodos = MutableStateFlow<List<TodoWithNew>>(emptyList())
    private val _todoEvent = MutableSharedFlow<TodoEvent>()
    private val _searchText = MutableStateFlow("")
    private val _selectedDayOfWeeks: MutableStateFlow<List<SelectableDayOfWeek>> =
        MutableStateFlow(emptyList())
    private val _homies: MutableStateFlow<List<SelectableHomy>> = MutableStateFlow(emptyList())
    private val _selectedDayOfWeeksString: MutableStateFlow<String> = MutableStateFlow("")
    private val _selectedHomiesString: MutableStateFlow<String> = MutableStateFlow("")
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
        _selectedDayOfWeeksString.asStateFlow()
    val selectedHomies: StateFlow<String> =
        _selectedHomiesString.asStateFlow()

    fun rollbackUiData() {
        viewModelScope.launch {
            setSelectableWeek()
            fetchHomies()
            getFilteredTodo(null, null)
        }
    }

    private fun transformSelectedDaysToString() {
        val selectedDayOfWeeks = selectableWeek.value.filter { dayOfWeek -> dayOfWeek.isSelected }
        val selectedDayOfWeeksString =
            if (selectedDayOfWeeks.count() != 7) selectedDayOfWeeks.joinToString(", ") { week -> week.dayOfWeek }
            else EVERY_DAY
        _selectedDayOfWeeksString.value = selectedDayOfWeeksString
    }

    private fun transformSelectedHomiesToString() {
        val selectedHomies = homies.value.filter { homy -> homy.isSelected }
        val selectedHomiesString =
            if (selectedHomies.count() > 1) String.format(
                HOMY_COUNT_FORMAT,
                selectedHomies[0].name,
                selectedHomies.count() - 1
            )
            else selectedHomies.joinToString("") { homy -> homy.name }
        _selectedHomiesString.value = selectedHomiesString
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
            getFilteredTodo(dayOfWeeks, onboardingIds)
        }
    }

    private suspend fun getFilteredTodo(dayOfWeeks: List<String>?, onboardingIds: List<Int>?) {
        getFilteredTodoUseCase(dayOfWeeks, onboardingIds)
            .onSuccess { filteredTodo ->
                transformSelectedHomiesToString()
                transformSelectedDaysToString()
                originalTodos.value = filteredTodo.todos
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

    private fun setSelectableWeek() {
        _selectedDayOfWeeks.value =
            WEEK.map { dayOfWeek -> SelectableDayOfWeek(dayOfWeek = dayOfWeek) }
    }

    private suspend fun fetchHomies() {
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
                    originalTodos.value
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
                    fetchFilteredTodo()
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
        private const val EVERY_DAY = "매일"
        private const val HOMY_COUNT_FORMAT = "%s 외 %d명"
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

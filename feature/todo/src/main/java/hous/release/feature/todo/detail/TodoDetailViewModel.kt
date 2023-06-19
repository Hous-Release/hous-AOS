package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.todo.FilteredTodo
import hous.release.domain.usecase.SearchRuleUseCase
import hous.release.domain.usecase.todo.GetFilteredTodoUseCase
import hous.release.domain.usecase.todo.GetHomiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getHomiesUseCase: GetHomiesUseCase,
    private val getFilteredTodoUseCase: GetFilteredTodoUseCase,
    private val searchRuleUseCase: SearchRuleUseCase
) : ViewModel() {
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

    fun setFilteredTodo(
        dayOfWeeks: List<String>?,
        onboardingIds: List<Int>?
    ) {
        viewModelScope.launch {
            val result = getFilteredTodoUseCase(dayOfWeeks, onboardingIds)
            _filteredTodo.value = if (searchText.value.isNotBlank()) result.copy(
                todos = searchRuleUseCase(
                    searchText.value,
                    result.todos
                )
            )
            else result
        }
    }

    private fun setSelectableWeek() {
        _selectedDayOfWeeks.value =
            WEEK.map { dayOfWeek -> SelectableDayOfWeek(dayOfWeek = dayOfWeek) }
    }

    private fun setHomies() {
        viewModelScope.launch {
            _homies.value = getHomiesUseCase().map { homy ->
                SelectableHomy(
                    id = homy.id,
                    name = homy.name,
                    homyType = homy.homyType
                )
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

    }

    companion object {
        private val WEEK = listOf("월", "화", "수", "목", "금", "토", "일")
    }
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

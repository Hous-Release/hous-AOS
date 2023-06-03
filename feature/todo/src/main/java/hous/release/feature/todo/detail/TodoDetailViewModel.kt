package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
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
    private val getHomiesUseCase: GetHomiesUseCase
) : ViewModel() {
    private val _week: MutableStateFlow<List<SelectableDayOfWeek>> =
        MutableStateFlow(emptyList())
    private val _homies: MutableStateFlow<List<SelectableHomy>> = MutableStateFlow(emptyList())

    val week: StateFlow<List<SelectableDayOfWeek>> = _week.asStateFlow()
    val homies = _homies.asStateFlow()
//    val selectedDays: StateFlow<String> =
//        _week
//            .map { selectedDays ->
//                selectedDays.filter { b -> b }
//            }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = ""
//            )

    init {
        setHomies()
    }

    private fun setWeek() {
        _week.value = WEEK.map { dayOfWeek -> SelectableDayOfWeek(dayOfWeek = dayOfWeek) }
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

    fun selectDay(index: Int) {
        _week.value = _week.value
            .mapIndexed { idx, selectableDayOfWeek ->
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

    companion object {
        val WEEK = listOf("월", "화", "수", "목", "금", "토", "일")
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

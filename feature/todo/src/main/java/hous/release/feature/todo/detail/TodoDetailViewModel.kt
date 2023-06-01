package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.usecase.todo.GetHomiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getHomiesUseCase: GetHomiesUseCase
) : ViewModel() {
    private val _isSelectedDay: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(listOf(false, false, false, false, false, false, false))
    private val _homies: MutableStateFlow<List<SelectableHomy>> = MutableStateFlow(emptyList())

    val isSelectedDay: StateFlow<List<Boolean>> = _isSelectedDay.asStateFlow()
    val homies = _homies.asStateFlow()

    init {
        setHomies()
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
        _isSelectedDay.value = _isSelectedDay.value
            .mapIndexed { idx, isSelected -> if (idx == index) !isSelected else isSelected }
    }

    fun selectHomy(index: Int) {
        _homies.value = _homies.value.mapIndexed { idx, selectableHomy ->
            if (idx == index) selectableHomy.copy(isSelected = !selectableHomy.isSelected)
            else selectableHomy
        }
    }
}

data class SelectableHomy(
    val id: Int,
    val isSelected: Boolean = false,
    val name: String,
    val homyType: HomyType
)

package hous.release.feature.todo.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor() : ViewModel() {
    val _isSelectedDay: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(listOf(false, false, false, false, false, false, false))
    val isSelectedDay: StateFlow<List<Boolean>> = _isSelectedDay.asStateFlow()

    fun selectDay(index: Int) {
        _isSelectedDay.value = _isSelectedDay.value
            .mapIndexed { idx, b -> if (idx == index) !b else b }
    }
}
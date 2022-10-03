package hous.release.android.presentation.todo.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.usecase.GetDailyTodosUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val dailyTodosUseCase: GetDailyTodosUseCase
) : ViewModel() {
    private val _dailyToDos: MutableStateFlow<List<TodoMain>> = MutableStateFlow(emptyList())
    val dailyToDos = _dailyToDos.asStateFlow()

    private val _dailyTabCurrIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val dailyTabCurrIndex = _dailyTabCurrIndex.asStateFlow()

    init {
        fetchDailyToDos()
    }

    private fun fetchDailyToDos() {
        viewModelScope.launch {
            dailyTodosUseCase()
                .onSuccess { result ->
                    _dailyToDos.value = result
                }
                .onFailure {
                    Timber.e("error: ${it.message}")
                }
        }
    }

    fun setTabCurrIndex(index: Int) {
        _dailyTabCurrIndex.value = index
    }
}

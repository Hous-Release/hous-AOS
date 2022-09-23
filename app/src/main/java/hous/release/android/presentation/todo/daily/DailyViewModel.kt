package hous.release.android.presentation.todo.daily

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.ToDoMain
import hous.release.domain.usecase.GetDailyToDosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val dailyToDosUseCase: GetDailyToDosUseCase
) : ViewModel() {
    private val _dailyToDos: MutableStateFlow<List<ToDoMain>> = MutableStateFlow(emptyList())
    val dailyToDos = _dailyToDos.asStateFlow()

    init {
        fetchDailyToDos()
    }

    private fun fetchDailyToDos() {
        viewModelScope.launch {
            dailyToDosUseCase()
                .onSuccess { result ->
                    _dailyToDos.value = result
                    Log.d("sdafkj", "success $result")
                }
                .onFailure {
                    Timber.d("error : ${it.message}")
                    Log.d("sdafkj", "success ${it.message}")
                }
        }
    }
}

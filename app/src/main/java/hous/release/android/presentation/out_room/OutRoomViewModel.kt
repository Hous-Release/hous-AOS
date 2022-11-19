package hous.release.android.presentation.out_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.SplashState
import hous.release.domain.entity.response.MyToDo
import hous.release.domain.repository.SettingsRepository
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OutRoomViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    private val _myToDoCnt = MutableStateFlow(-1)
    val myToDoCnt: StateFlow<Int> = _myToDoCnt.asStateFlow()

    private val _myToDos = MutableStateFlow<List<MyToDo>>(emptyList())
    val myToDos: StateFlow<List<MyToDo>> = _myToDos.asStateFlow()

    private val _isSuccessDeleteRoom = MutableSharedFlow<Boolean>()
    val isSuccessDeleteRoom: SharedFlow<Boolean> = _isSuccessDeleteRoom.asSharedFlow()

    fun getSettingsMyToDo() {
        viewModelScope.launch {
            settingsRepository.getSettingsMyToDo()
                .onSuccess { response ->
                    _myToDoCnt.value = response.myTodosCnt
                    _myToDos.value = response.myTodos
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun deleteRoom() {
        viewModelScope.launch {
            settingsRepository.deleteRoom()
                .onSuccess { response ->
                    _isSuccessDeleteRoom.emit(response)
                    setSplashStateUseCase(SplashState.ENTER_ROOM)
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}

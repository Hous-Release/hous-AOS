package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.UiEvent
import hous.release.domain.entity.SplashState
import hous.release.domain.entity.response.Room
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EnterRoomCodeViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    val roomCode = MutableStateFlow<String>("")

    private val _isSuccessGetRoom = MutableSharedFlow<Boolean>()
    val isSuccessGetRoom: SharedFlow<Boolean> = _isSuccessGetRoom.asSharedFlow()

    private val _roomInfo = MutableStateFlow(Room())
    val roomInfo: StateFlow<Room> = _roomInfo.asStateFlow()

    private val _enterRoomUiEvent = MutableSharedFlow<UiEvent>()
    val enterRoomUiEvent: SharedFlow<UiEvent> = _enterRoomUiEvent.asSharedFlow()

    private val _isFullCapacity = MutableSharedFlow<Boolean>()
    val isFullCapacity: SharedFlow<Boolean> = _isFullCapacity.asSharedFlow()

    fun resetRoomCode() {
        roomCode.value = ""
    }

    fun getEnterRoomCode() {
        viewModelScope.launch {
            enterRoomRepository.getEnterRoomCode(roomCode.value)
                .onSuccess { response ->
                    _roomInfo.value = response
                    _isSuccessGetRoom.emit(true)
                }
                .onFailure {
                    Timber.d(it.message.toString())
                }
        }
    }

    fun postEnterRoomId() {
        viewModelScope.launch {
            _enterRoomUiEvent.emit(UiEvent.LOADING)
            enterRoomRepository.postEnterRoomId(roomInfo.value.roomId.toString())
                .onSuccess {
                    setSplashStateUseCase(SplashState.MAIN)
                    _enterRoomUiEvent.emit(UiEvent.SUCCESS)
                }
                .onFailure { throwable ->
                    Timber.d(throwable.message)
                    _enterRoomUiEvent.emit(UiEvent.ERROR)
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            FULL_CAPACITY -> _isFullCapacity.emit(true)
                        }
                    }
                }
        }
    }

    companion object {
        private const val FULL_CAPACITY = 403
    }
}

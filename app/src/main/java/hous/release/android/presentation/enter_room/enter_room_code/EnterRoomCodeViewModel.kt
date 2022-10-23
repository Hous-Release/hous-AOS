package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.DomainGetRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EnterRoomCodeViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) : ViewModel() {
    val roomCode = MutableStateFlow<String>("")

    private val _isSuccessGetRoom = MutableSharedFlow<Boolean>()
    val isSuccessGetRoom: SharedFlow<Boolean> = _isSuccessGetRoom.asSharedFlow()

    private val _roomInfo = MutableStateFlow(DomainGetRoomResponse())
    val roomInfo: StateFlow<DomainGetRoomResponse> = _roomInfo.asStateFlow()

    private val _isSuccessEnterRoom = MutableSharedFlow<Boolean>()
    val isSuccessEnterRoom: SharedFlow<Boolean> = _isSuccessEnterRoom.asSharedFlow()

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
            enterRoomRepository.postEnterRoomId(roomInfo.value.roomId.toString())
                .onSuccess {
                    _isSuccessEnterRoom.emit(true)
                }
                .onFailure {
                    Timber.d(it.message.toString())
                }
        }
    }
}

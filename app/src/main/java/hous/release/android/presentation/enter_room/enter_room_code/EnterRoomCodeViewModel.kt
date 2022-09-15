package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.DomainGetRoomResponse
import hous.release.domain.repository.EnterRoomRepository
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
class EnterRoomCodeViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) : ViewModel() {
    val roomCode = MutableStateFlow<String>("")

    private val _isSuccessGetRoom = MutableSharedFlow<Boolean>()
    val isSuccessGetRoom: SharedFlow<Boolean> = _isSuccessGetRoom.asSharedFlow()

    private val _roomInfo = MutableStateFlow(DomainGetRoomResponse())
    val roomInfo: StateFlow<DomainGetRoomResponse> = _roomInfo.asStateFlow()

    fun getEnterRoomCode() {
        viewModelScope.launch {
            enterRoomRepository.getEnterRoomCode(roomCode.value)
                .onSuccess { response ->
                    _roomInfo.value = response
                    _isSuccessGetRoom.emit(true)
                    Timber.tag("EnterRoom - getEnterRoomCode").d(response.toString())
                }
                .onFailure {
                    Timber.tag("EnterRoom - getEnterRoomCode").d(it.message.toString())
                }
        }
    }
}
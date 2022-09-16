package hous.release.android.presentation.enter_room.create_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.request.DomainCreateRoomRequest
import hous.release.domain.entity.response.DomainCreateRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) : ViewModel() {
    val roomName = MutableStateFlow<String>("")

    private val _isSuccessCreateRoom = MutableSharedFlow<Boolean>()
    val isSuccessCreateRoom: SharedFlow<Boolean> = _isSuccessCreateRoom

    var newRoomInfo: DomainCreateRoomResponse = DomainCreateRoomResponse()
        private set

    fun postCreateRoom() {
        viewModelScope.launch {
            enterRoomRepository.postCreateRoom(DomainCreateRoomRequest(roomName.value))
                .onSuccess { response ->
                    newRoomInfo = response
                    _isSuccessCreateRoom.emit(true)
                }
                .onFailure {
                    Timber.tag("EnterRoom - postCreateRoom").d(it.message.toString())
                }
        }
    }
}

package hous.release.android.presentation.enter_room.create_room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.CreateRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) : ViewModel() {
    val roomName = MutableLiveData<String>("")

    private val _newRoom = MutableSharedFlow<CreateRoomResponse>()
    val newRoom: SharedFlow<CreateRoomResponse> = _newRoom

    fun postCreateRoom() {
        viewModelScope.launch {
            enterRoomRepository.postCreateRoom(CreateRoomRequest(requireNotNull(roomName.value)))
                .onSuccess { response ->
                    _newRoom.emit(response.data)
                }
                .onFailure {
                    Timber.tag("EnterRoom - postCreateRoom").d(it.message.toString())
                }
        }
    }
}

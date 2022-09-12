package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.EnterRoomCodeResponse
import hous.release.domain.repository.EnterRoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EnterRoomCodeViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
) : ViewModel() {
    val roomCode = MutableLiveData<String>("")

    private val _roomInfo = MutableStateFlow(EnterRoomCodeResponse())
    val roomInfo: StateFlow<EnterRoomCodeResponse> = _roomInfo

    fun getEnterRoomCode() {
        viewModelScope.launch {
            enterRoomRepository.getEnterRoomCode(requireNotNull(roomCode.value))
                .onSuccess { response ->
                    _roomInfo.value = response.data
                    Timber.tag("EnterRoom - getEnterRoomCode").d(response.data.toString())
                }
                .onFailure {
                    Timber.tag("EnterRoom - getEnterRoomCode").d(it.message.toString())
                }
        }
    }
}

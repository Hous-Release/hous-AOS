package hous.release.android.presentation.enter_room.create_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.SplashState
import hous.release.domain.entity.response.NewRoom
import hous.release.domain.repository.EnterRoomRepository
import hous.release.domain.usecase.SetSplashStateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository,
    private val setSplashStateUseCase: SetSplashStateUseCase
) : ViewModel() {
    val roomName = MutableStateFlow<String>("")

    private val _isSuccessCreateRoom = MutableSharedFlow<Boolean>()
    val isSuccessCreateRoom: SharedFlow<Boolean> = _isSuccessCreateRoom.asSharedFlow()

    var newRoomInfo: NewRoom = NewRoom()
        private set

    fun resetRoomName() {
        roomName.value = ""
    }

    fun postCreateRoom() {
        viewModelScope.launch {
            enterRoomRepository.postCreateRoom(roomName.value)
                .onSuccess { response ->
                    newRoomInfo = response
                    setSplashStateUseCase(SplashState.MAIN)
                    _isSuccessCreateRoom.emit(true)
                }
                .onFailure {
                    Timber.d(it.message.toString())
                }
        }
    }
}

package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

@HiltViewModel
class EnterRoomCodeViewModel @Inject constructor(
    private val enterRoomRepository: EnterRoomRepository
): ViewModel() {
    val roomCode = MutableLiveData<String>("")
}

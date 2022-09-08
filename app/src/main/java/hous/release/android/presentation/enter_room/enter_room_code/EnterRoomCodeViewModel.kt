package hous.release.android.presentation.enter_room.enter_room_code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnterRoomCodeViewModel : ViewModel() {
    val roomCode = MutableLiveData<String>("")
}

package hous.release.android.presentation.enter_room.create_room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateRoomViewModel : ViewModel() {
    val roomName = MutableLiveData<String>("")
}

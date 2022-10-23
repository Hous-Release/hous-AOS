package hous.release.android.presentation.hous

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class EditHousNameViewModel : ViewModel() {
    var originalRoomName = ""
        private set
    val roomName = MutableStateFlow<String>("")

    fun initOriginalRoomName(name: String) {
        originalRoomName = name
        roomName.value = originalRoomName
    }
}

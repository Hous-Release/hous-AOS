package hous.release.android.presentation.hous

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.UiEvent
import hous.release.domain.repository.HousRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditHousNameViewModel @Inject constructor(
    private val housRepository: HousRepository
) : ViewModel() {
    var originalRoomName = ""
        private set
    val roomName = MutableStateFlow<String>("")

    private val _editHousNameUiEvent = MutableSharedFlow<UiEvent>()
    val editHousNameUiEvent: SharedFlow<UiEvent> = _editHousNameUiEvent.asSharedFlow()

    fun initOriginalRoomName(name: String) {
        originalRoomName = name
        roomName.value = originalRoomName
    }

    fun getIsEdited(): Boolean = originalRoomName != roomName.value

    fun putHousName() {
        viewModelScope.launch {
            _editHousNameUiEvent.emit(UiEvent.LOADING)
            housRepository.putHousName(roomName.value)
                .onSuccess {
                    _editHousNameUiEvent.emit(UiEvent.SUCCESS)
                }
                .onFailure {
                    Timber.d(it.message.toString())
                    _editHousNameUiEvent.emit(UiEvent.ERROR)
                }
        }
    }
}

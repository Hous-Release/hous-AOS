package hous.release.android.presentation.hous

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.RequestState
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

    private val _editHousNameRequestState = MutableSharedFlow<RequestState>()
    val editHousNameRequestState: SharedFlow<RequestState> = _editHousNameRequestState.asSharedFlow()

    fun initOriginalRoomName(name: String) {
        originalRoomName = name
        roomName.value = originalRoomName
    }

    fun getIsEdited(): Boolean = originalRoomName != roomName.value

    fun putHousName() {
        viewModelScope.launch {
            _editHousNameRequestState.emit(RequestState.LOADING)
            housRepository.putHousName(roomName.value)
                .onSuccess { isSuccess ->
                    _editHousNameRequestState.emit(RequestState.SUCCESS)
                }
                .onFailure {
                    Timber.d(it.message.toString())
                    _editHousNameRequestState.emit(RequestState.FAILED)
                }
        }
    }
}

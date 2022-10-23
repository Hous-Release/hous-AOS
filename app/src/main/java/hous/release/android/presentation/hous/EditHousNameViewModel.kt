package hous.release.android.presentation.hous

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.HousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isSuccessEditHousName = MutableStateFlow<Boolean>(false)
    val isSuccessEditHousName: StateFlow<Boolean> = _isSuccessEditHousName.asStateFlow()

    fun initOriginalRoomName(name: String) {
        originalRoomName = name
        roomName.value = originalRoomName
    }

    fun putHousName() {
        viewModelScope.launch {
            housRepository.putHousName(roomName.value)
                .onSuccess { isSuccess ->
                    _isSuccessEditHousName.value = isSuccess
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}

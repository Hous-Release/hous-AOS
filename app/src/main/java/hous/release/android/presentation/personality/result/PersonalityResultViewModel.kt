package hous.release.android.presentation.personality.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.ImageDownloader
import hous.release.android.util.UiEvent
import hous.release.domain.entity.response.PersonalityResult
import hous.release.domain.usecase.GetPersonalityResultUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonalityResultViewModel @Inject constructor(
    private val application: Application,
    private val getPersonalityResultUseCase: GetPersonalityResultUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonalityResult())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val imageDownloader = ImageDownloader(application)

    fun getPersonalityResult(personalityColor: String) {
        viewModelScope.launch {
            getPersonalityResultUseCase(personalityColor)
                .onSuccess { response ->
                    _uiState.value = response
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    fun downloadImage() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.LOADING)
            try {
                imageDownloader.downloadAndSaveImage(
                    _uiState.value.firstDownloadImageUrl,
                    _uiState.value.color.name + "1"
                )
                imageDownloader.downloadAndSaveImage(
                    _uiState.value.secondDownloadImageUrl,
                    _uiState.value.color.name + "2"
                )
                _uiEvent.emit(UiEvent.SUCCESS)
            } catch (e: Exception) {
                Timber.e(e.message)
                _uiEvent.emit(UiEvent.ERROR)
            }
        }
    }
}

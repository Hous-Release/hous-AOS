package hous.release.android.presentation.practice

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
@Inject constructor(
    private val photoSaver: PhotoSaverRepository
) : ViewModel() {
    val uiState = MutableStateFlow(PhotoUiState(emptyList()))

    init {
        viewModelScope.launch {
            photoSaver.removeAllFile()
        }
        uiState.update {
            it.copy(
                photos = photoSaver.fetchPhotos()
            )
        }
    }

    fun fetchPhotos() = photoSaver.fetchPhotos()
    fun isEmpty() = photoSaver.isEmpty()
    fun canAddPhoto() = photoSaver.canAddPhoto()

    fun cacheRemotePhotosFrom(urls: List<String>) {
        viewModelScope.launch {
            photoSaver.cacheFromUrls(urls)
            uiState.update {
                it.copy(
                    photos = photoSaver.fetchPhotos()
                )
            }
        }
    }

    fun loadImagesFrom(uris: List<Uri>) {
        viewModelScope.launch {
            uiState.update {
                it.copy(photos = List(uris.size) { null })
            }
            photoSaver.cacheFromUris(uris)
            uiState.update {
                it.copy(
                    photos = photoSaver.fetchPhotos()
                )
            }
        }
    }

    fun removePhoto(file: File) {
        viewModelScope.launch {
            runCatching {
                photoSaver.removeFile(file)
            }.onSuccess {
                uiState.update {
                    it.copy(
                        photos = photoSaver.fetchPhotos()
                    )
                }
            }.onFailure {
                Timber.e(it.stackTraceToString())
            }
        }
    }
}

data class PhotoUiState(
    val photos: List<File?>
)

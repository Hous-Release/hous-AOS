package hous.release.android.presentation.practice

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class PhotoViewModel(
    private val photoSaver: PhotoSaverRepository,
    private val bitmapManager: BitmapManager
) : ViewModel() {
    val uiState = MutableStateFlow(PhotoUiState(emptyList()))

    init {
        uiState.update {
            it.copy(
                photos = photoSaver.fetchPhotos()
            )
        }
    }

    fun loadImagesFrom(uris: List<Uri>) {
        viewModelScope.launch {
            uiState.update {
                it.copy(
                    photos = List(uris.size) { null }
                )
            }
            // skeletone test
//            delay(2000)
            photoSaver.cacheFromUris(uris)
            uiState.update {
                it.copy(
                    photos = photoSaver.fetchPhotos()
                )
            }
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

    fun removePhoto(file: File) {
        viewModelScope.launch {
            photoSaver.removeFile(file)
            uiState.update {
                it.copy(
                    photos = photoSaver.fetchPhotos()
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            photoSaver.removeAllFile()
        }
    }
}

data class PhotoUiState(
    val photos: List<File?>
)

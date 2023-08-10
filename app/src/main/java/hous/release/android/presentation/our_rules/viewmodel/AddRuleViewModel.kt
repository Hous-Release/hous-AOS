package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.di.AddRule
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.util.event.Reducer
import hous.release.domain.usecase.rule.AddRuleUseCase
import hous.release.domain.value.PhotoUri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddRuleViewModel @Inject constructor(
    private val addRuleUseCase: AddRuleUseCase,
    @AddRule private val reducer: Reducer<AddRuleState, AddRuleEvent>
) : ViewModel() {

    private val uiEvents = Channel<AddRuleEvent>()
    val uiState = uiEvents
        .receiveAsFlow()
        .runningFold(AddRuleState(), reducer::dispatch)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddRuleState())

    private val _sideEffect: Channel<AddRuleSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private fun canAddPhotos(photoSize: Int): Boolean {
        return (uiState.value.photos.size + photoSize) <= 5
    }

    fun changeName(name: String) {
        viewModelScope.launch {
            uiEvents.send(AddRuleEvent.ChangeName(name))
        }
    }

    fun changeDescription(description: String) {
        viewModelScope.launch {
            uiEvents.send(AddRuleEvent.ChangeDescription(description))
        }
    }

    fun loadImage(photoUris: List<PhotoUri>) {
        viewModelScope.launch {
            if (canAddPhotos(photoUris.size)) {
                Timber.e("loadImage() - photoUris: $photoUris")
                uiEvents.send(AddRuleEvent.LoadImage(photoUris))
                return@launch
            }
            _sideEffect.send(AddRuleSideEffect.ShowLimitImageToast)
        }
    }

    fun deleteImage(photo: PhotoUiModel) {
        viewModelScope.launch {
            uiEvents.send(AddRuleEvent.DeleteImage(photo))
        }
    }

    fun addRule() {
        viewModelScope.launch {
            _sideEffect.send(AddRuleSideEffect.LoadingBar(true))
            runCatching {
                val photoUris = uiState.value.photos.map { photo ->
                    photo.filePath?.let { path ->
                        PhotoUri(path)
                    } ?: throw NullPointerException("filePath가 null이다")
                }
                addRuleUseCase(
                    uiState.value.description,
                    uiState.value.name,
                    photoUris
                )
            }.onSuccess {
                _sideEffect.send(AddRuleSideEffect.PopBackStack)
            }.onFailure { e ->
                if (e is HttpException) {
                    when (e.code()) {
                        DUPLICATE_CODE -> _sideEffect.send(AddRuleSideEffect.DuplicateToast)
                        LIMITED_RULE_COUNT_CODE -> _sideEffect.send(AddRuleSideEffect.ShowLimitRuleCountDialog)
                    }
                } else {
                    Timber.e("addRule() - onFailure() - e: ${e.stackTraceToString()}")
                    _sideEffect.send(AddRuleSideEffect.LoadingBar(false))
                }
            }
        }
    }

    private companion object {
        const val DUPLICATE_CODE = 409
        const val LIMITED_RULE_COUNT_CODE = 403
    }
}

sealed class AddRuleSideEffect {
    object IDLE : AddRuleSideEffect()
    data class LoadingBar(val isLoading: Boolean) : AddRuleSideEffect()
    object ShowLimitImageToast : AddRuleSideEffect()
    object ShowLimitRuleCountDialog : AddRuleSideEffect()
    object DuplicateToast : AddRuleSideEffect()
    object PopBackStack : AddRuleSideEffect()
}

data class AddRuleState(
    val name: String = "",
    val description: String = "",
    val photos: List<PhotoUiModel> = emptyList()
)

sealed class AddRuleEvent {
    data class ChangeDescription(val description: String) : AddRuleEvent()
    data class ChangeName(val name: String) : AddRuleEvent()
    data class LoadImage(val photoUris: List<PhotoUri>) : AddRuleEvent()
    data class DeleteImage(val photo: PhotoUiModel) : AddRuleEvent()
}

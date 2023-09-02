package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.di.UpdateRule
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.util.event.Reducer
import hous.release.domain.entity.Photo
import hous.release.domain.usecase.rule.UpdateRuleUseCase
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
class UpdateRuleViewModel @Inject constructor(
    private val updateRuleUseCase: UpdateRuleUseCase,
    @UpdateRule private val reducer: Reducer<UpdateRuleState, UpdateRuleEvent>
) : ViewModel() {
    private val uiEvents = Channel<UpdateRuleEvent>()
    val uiState = uiEvents
        .receiveAsFlow()
        .runningFold(UpdateRuleState(), reducer::dispatch)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UpdateRuleState())

    private val _sideEffect: Channel<UpdateRuleSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private fun canAddPhotos(photoSize: Int): Boolean {
        return (uiState.value.photos.size + photoSize) <= 5
    }

    fun init(detailRule: DetailRuleUiModel) {
        viewModelScope.launch {
            uiEvents.send(
                UpdateRuleEvent.InitRule(
                    detailRule.id,
                    detailRule.name,
                    detailRule.description,
                    detailRule.photos
                )
            )
        }
    }

    fun isChangeRuleContent(detailRule: DetailRuleUiModel) = uiState.value.run {
        detailRule.name != name || detailRule.description != description || detailRule.photos != photos
    }

    fun changeName(name: String) {
        viewModelScope.launch {
            uiEvents.send(UpdateRuleEvent.ChangeName(name))
        }
    }

    fun changeDescription(description: String) {
        viewModelScope.launch {
            uiEvents.send(UpdateRuleEvent.ChangeDescription(description))
        }
    }

    fun loadImage(photos: List<Photo>) {
        viewModelScope.launch {
            if (canAddPhotos(photos.size)) {
                Timber.e("loadImage() - photoUris: $photos")
                uiEvents.send(UpdateRuleEvent.LoadImage(photos))
                return@launch
            }
            _sideEffect.send(UpdateRuleSideEffect.ShowLimitImageToast)
        }
    }

    fun deleteImage(photo: PhotoUiModel) {
        viewModelScope.launch {
            uiEvents.send(UpdateRuleEvent.DeleteImage(photo))
        }
    }

    fun updateRule() {
        viewModelScope.launch {
            _sideEffect.send(UpdateRuleSideEffect.LoadingBar(true))
            runCatching {
                val photoUris = uiState.value.photos.map { photo ->
                    photo.filePath?.let { path ->
                        Photo.from(path)
                    } ?: throw NullPointerException("filePath가 null이다")
                }
                updateRuleUseCase(
                    uiState.value.id,
                    uiState.value.description,
                    uiState.value.name,
                    photoUris
                )
            }.onSuccess {
                _sideEffect.send(UpdateRuleSideEffect.LoadingBar(false))
                _sideEffect.send(UpdateRuleSideEffect.PopBackStack)
            }.onFailure { e ->
                if (e is HttpException) {
                    when (e.code()) {
                        DUPLICATE_CODE -> _sideEffect.send(UpdateRuleSideEffect.DuplicateToast)
                        else -> Timber.e("addRule() - onFailure() - e: ${e.stackTraceToString()}")
                    }
                    _sideEffect.send(UpdateRuleSideEffect.LoadingBar(false))
                } else {
                    Timber.e("updateRule() - onFailure() - e: ${e.stackTraceToString()}")
                    _sideEffect.send(UpdateRuleSideEffect.LoadingBar(false))
                }
            }
        }
    }

    private companion object {
        const val DUPLICATE_CODE = 409
    }
}

sealed class UpdateRuleSideEffect {
    object IDLE : UpdateRuleSideEffect()
    data class LoadingBar(val isLoading: Boolean) : UpdateRuleSideEffect()
    object ShowLimitImageToast : UpdateRuleSideEffect()
    object DuplicateToast : UpdateRuleSideEffect()
    object PopBackStack : UpdateRuleSideEffect()
}

data class UpdateRuleState(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val photos: List<PhotoUiModel> = emptyList()
)

sealed class UpdateRuleEvent {
    data class InitRule(
        val id: Int,
        val name: String,
        val description: String,
        val photos: List<PhotoUiModel>
    ) : UpdateRuleEvent()

    data class ChangeDescription(val description: String) : UpdateRuleEvent()
    data class ChangeName(val name: String) : UpdateRuleEvent()
    data class LoadImage(val photos: List<Photo>) : UpdateRuleEvent()
    data class DeleteImage(val photo: PhotoUiModel) : UpdateRuleEvent()
}

package hous.release.android.presentation.our_rules.event

import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.presentation.our_rules.viewmodel.AddRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.AddRuleState
import hous.release.android.util.event.Reducer
import timber.log.Timber

class AddRuleReducer : Reducer<AddRuleState, AddRuleEvent> {
    override fun dispatch(state: AddRuleState, event: AddRuleEvent): AddRuleState {
        return when (event) {
            is AddRuleEvent.ChangeDescription -> {
                state.copy(description = event.description)
            }

            is AddRuleEvent.ChangeName -> {
                state.copy(name = event.name)
            }

            is AddRuleEvent.LoadImage -> {
                val newPhotos = event.photoUris.map { uri ->
                    PhotoUiModel(filePath = uri.path)
                }
                val updatedPhotos = state.photos + newPhotos
                Timber.e("LoadImage: updatedPhotos: $updatedPhotos")
                state.copy(
                    photos = updatedPhotos
                )
            }

            is AddRuleEvent.DeleteImage -> {
                val filteredPhotos = state.photos.filter { photo ->
                    photo != event.photo
                }
                state.copy(
                    photos = filteredPhotos
                )
            }
        }
    }
}

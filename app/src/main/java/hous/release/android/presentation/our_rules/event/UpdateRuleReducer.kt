package hous.release.android.presentation.our_rules.event

import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.presentation.our_rules.viewmodel.UpdateRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.UpdateRuleState
import hous.release.android.util.event.Reducer
import timber.log.Timber
import javax.inject.Inject

class UpdateRuleReducer @Inject constructor() : Reducer<UpdateRuleState, UpdateRuleEvent> {
    override fun dispatch(state: UpdateRuleState, event: UpdateRuleEvent): UpdateRuleState {
        return when (event) {
            is UpdateRuleEvent.InitRule -> {
                state.copy(
                    id = event.id,
                    name = event.name,
                    description = event.description,
                    photos = event.photos
                )
            }

            is UpdateRuleEvent.ChangeDescription -> {
                state.copy(description = event.description)
            }

            is UpdateRuleEvent.ChangeName -> {
                state.copy(name = event.name)
            }

            is UpdateRuleEvent.LoadImage -> {
                val newPhotos = event.photos.map { photo ->
                    PhotoUiModel.from(photo)
                }
                val updatedPhotos = state.photos + newPhotos
                Timber.e("LoadImage: updatedPhotos: $updatedPhotos")
                state.copy(
                    photos = updatedPhotos
                )
            }

            is UpdateRuleEvent.DeleteImage -> {
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

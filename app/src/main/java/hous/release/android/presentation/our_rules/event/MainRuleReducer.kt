package hous.release.android.presentation.our_rules.event

import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.presentation.our_rules.viewmodel.MainRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.MainRulesState
import hous.release.android.util.event.Reducer
import hous.release.domain.entity.rule.DetailRule
import timber.log.Timber
import javax.inject.Inject

class MainRuleReducer @Inject constructor() : Reducer<MainRulesState, MainRulesEvent> {
    override fun dispatch(state: MainRulesState, event: MainRulesEvent): MainRulesState {
        return when (event) {
            is MainRulesEvent.Refresh -> {
                MainRulesState(
                    originRules = event.rules,
                    filteredRules = event.rules
                )
            }

            is MainRulesEvent.FetchMainRules -> {
                state.copy(
                    originRules = event.rules,
                    filteredRules = event.rules
                )
            }

            is MainRulesEvent.FetchDetailRule -> {
                state.copy(
                    detailRule = event.rule.toUiModel()
                )
            }

            is MainRulesEvent.LoadedImage -> {
                val photoUris = event.photoUris
                Timber.e("LoadedImage photoUris: $photoUris")
                val updatedImages = state.detailRule.images.mapIndexed { index, photo ->
                    photo.copy(
                        filePath = photoUris[index]?.path
                    )
                }
                Timber.e("LoadedImage updatedImages: $updatedImages")
                state.copy(
                    detailRule = state.detailRule.copy(images = updatedImages)
                )
            }

            is MainRulesEvent.SearchRule -> {
                state.copy(
                    searchQuery = event.searchQuery,
                    filteredRules = event.filteredRules
                )
            }

            is MainRulesEvent.DeleteAllFile -> state
        }
    }

    private fun DetailRule.toUiModel() = DetailRuleUiModel(
        id = id,
        name = name,
        description = description,
        images = images.map { url ->
            PhotoUiModel(
                url = url
            )
        },
        updatedAt = updatedAt
    )
}

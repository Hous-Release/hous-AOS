package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.presentation.our_rules.model.PhotoUiModel
import hous.release.android.util.Reducer
import hous.release.data.repository.RulePhotoRepository
import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.enums.PhotoUri
import hous.release.domain.usecase.rule.GetDetailRuleUseCase
import hous.release.domain.usecase.rule.GetMainRulesUseCase
import hous.release.domain.usecase.search.SearchRuleUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainRuleViewModel @Inject constructor(
    private val photoSaver: RulePhotoRepository,
    private val getMainRulesUseCase: GetMainRulesUseCase,
    private val getDetailRuleUseCase: GetDetailRuleUseCase,
    private val searcher: SearchRuleUseCase
) : ViewModel(), Reducer<MainRulesState, MainRulesEvent> {

    private val uiEvents = Channel<MainRulesEvent>()
    val uiState = uiEvents.receiveAsFlow()
        .runningFold(MainRulesState(), ::dispatch)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainRulesState())

    init {
        fetchMainRules()
    }

    fun fetchMainRules() {
        viewModelScope.launch {
            runCatching { getMainRulesUseCase() }
                .onSuccess { rules ->
                    uiEvents.send(MainRulesEvent.FetchMainRules(rules))
                }
                .onFailure {
                    Timber.e(it.stackTraceToString())
                }
        }
    }

    fun searchRule(searchQuery: String) {
        viewModelScope.launch {
            uiEvents.send(MainRulesEvent.SearchRule(searchQuery))
        }
    }

    fun fetchDetailRule(id: Int) {
        viewModelScope.launch {
            runCatching { getDetailRuleUseCase(id) }
                .onSuccess { _detailRule: DetailRule ->
                    uiEvents.send(MainRulesEvent.FetchDetailRule(_detailRule))
                    // image Url을 photo Uri로 변환하는 작업
                    photoSaver.fetchRemotePhotosFlow(_detailRule.images).collectLatest {
                        Timber.d("fetchRemotePhotosFlow: $it")
                        uiEvents.send(MainRulesEvent.LoadedImage(it))
                    }
                }
                .onFailure {
                    Timber.e(it.stackTraceToString())
                }
        }
    }

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
                        isUploading = photoUris[index] == null,
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
                    filteredRules = searcher(event.searchQuery, state.originRules)
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
                url = url,
                isUploading = true
            )
        },
        updatedAt = updatedAt
    )

    private companion object {
        const val TEST_IMAGE_URL =
            "https://github.com/Hous-Release/hous-AOS/assets/87055456/e5aa4a25-cbde-4b13-ab3c-6cc002dbc00f"
    }
}

sealed class MainRulesEvent {
    data class LoadedImage(val photoUris: List<PhotoUri?>) : MainRulesEvent()

    data class Refresh(val rules: List<MainRule>) : MainRulesEvent()
    data class FetchMainRules(val rules: List<MainRule>) : MainRulesEvent()
    data class FetchDetailRule(val rule: DetailRule) : MainRulesEvent()
    data class SearchRule(val searchQuery: String) : MainRulesEvent()
    object DeleteAllFile : MainRulesEvent()
}

data class MainRulesState(
    val detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    val originRules: List<MainRule> = emptyList(),
    val filteredRules: List<MainRule> = emptyList(),
    val searchQuery: String = ""
)

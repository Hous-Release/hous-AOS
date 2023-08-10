package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.di.MainRules
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.android.util.event.Reducer
import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.repository.PhotoRepository
import hous.release.domain.usecase.rule.CanAddRuleUseCase
import hous.release.domain.usecase.rule.GetDetailRuleUseCase
import hous.release.domain.usecase.rule.GetMainRulesUseCase
import hous.release.domain.usecase.search.SearchRuleUseCase
import hous.release.domain.value.PhotoUri
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
    private val photoSaver: PhotoRepository,
    private val getMainRulesUseCase: GetMainRulesUseCase,
    private val getDetailRuleUseCase: GetDetailRuleUseCase,
    private val canAddRuleUseCase: CanAddRuleUseCase,
    private val searcher: SearchRuleUseCase,
    @MainRules private val reducer: Reducer<MainRulesState, MainRulesEvent>
) : ViewModel() {

    private val uiEvents = Channel<MainRulesEvent>()
    val uiState = uiEvents
        .receiveAsFlow()
        .runningFold(MainRulesState(), reducer::dispatch)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainRulesState())

    private val _sideEffect: Channel<MainRuleSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchMainRules()
    }

    fun canAddRule() {
        viewModelScope.launch {
            val canAddRule = canAddRuleUseCase()
            Timber.e("canAddRule: $canAddRule")
            _sideEffect.send(MainRuleSideEffect.ShowLimitedAddRuleDialog(canAddRule))
        }
    }

    fun fetchMainRules() {
        viewModelScope.launch {
            runCatching { getMainRulesUseCase() }.onSuccess { rules ->
                uiEvents.send(MainRulesEvent.FetchMainRules(rules))
            }.onFailure {
                Timber.e(it.stackTraceToString())
            }
        }
    }

    fun searchRule(searchQuery: String) {
        viewModelScope.launch {
            uiEvents.send(
                MainRulesEvent.SearchRule(
                    searchQuery,
                    searcher(searchQuery, uiState.value.originRules)
                )
            )
        }
    }

    fun fetchDetailRule(id: Int) {
        viewModelScope.launch {
            runCatching { getDetailRuleUseCase(id) }.onSuccess { _detailRule: DetailRule ->
                uiEvents.send(MainRulesEvent.FetchDetailRule(_detailRule))
                // image Url을 photo Uri로 변환하는 작업
                photoSaver.fetchRemotePhotosFlow(_detailRule.images).collectLatest {
                    Timber.d("fetchRemotePhotosFlow: $it")
                    uiEvents.send(MainRulesEvent.LoadedImage(it))
                }
            }.onFailure {
                Timber.e(it.stackTraceToString())
            }
        }
    }
}

sealed class MainRuleSideEffect {
    object IDLE : MainRuleSideEffect()
    data class ShowLimitedAddRuleDialog(val isShow: Boolean) : MainRuleSideEffect()
}

sealed class MainRulesEvent {
    data class LoadedImage(val photoUris: List<PhotoUri?>) : MainRulesEvent()

    data class Refresh(val rules: List<MainRule>) : MainRulesEvent()
    data class FetchMainRules(val rules: List<MainRule>) : MainRulesEvent()
    data class FetchDetailRule(val rule: DetailRule) : MainRulesEvent()
    data class SearchRule(val searchQuery: String, val filteredRules: List<MainRule>) :
        MainRulesEvent()

    object DeleteAllFile : MainRulesEvent()
}

data class MainRulesState(
    val detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    val originRules: List<MainRule> = emptyList(),
    val filteredRules: List<MainRule> = emptyList(),
    val searchQuery: String = ""
)

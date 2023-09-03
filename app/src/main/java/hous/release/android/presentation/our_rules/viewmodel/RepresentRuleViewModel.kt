package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.di.RepresentRules
import hous.release.android.presentation.our_rules.model.RepresentRuleUiModel
import hous.release.android.util.event.Reducer
import hous.release.domain.entity.rule.Rule
import hous.release.domain.usecase.rule.GetRulesUseCase
import hous.release.domain.usecase.rule.UpdateRepresentRulesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepresentRuleViewModel @Inject constructor(
    private val updateRepresentRulesUseCase: UpdateRepresentRulesUseCase,
    private val getRulesUseCase: GetRulesUseCase,
    @RepresentRules private val reducer: Reducer<RepresentRulesState, RepresentRulesEvent>
) : ViewModel() {

    private val uiEvents = Channel<RepresentRulesEvent>()
    val uiState: StateFlow<RepresentRulesState> = uiEvents
        .receiveAsFlow()
        .runningFold(RepresentRulesState(), reducer::dispatch)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RepresentRulesState())

    private val _sideEffect: Channel<RepresentRulesSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            val rules = getRulesUseCase()
            uiEvents.send(RepresentRulesEvent.FetchRules(rules))
        }
    }

    val isSavable get() = uiState.value.rules == uiState.value.originRules

    fun updateRuleBy(id: Int) {
        viewModelScope.launch {
            if (canAddRepresentRule()) {
                uiEvents.send(RepresentRulesEvent.UpdateRule(id))
                return@launch
            }
            _sideEffect.send(RepresentRulesSideEffect.ShowLimitRulesToast)
        }
    }

    fun saveRules() {
        viewModelScope.launch {
            _sideEffect.send(RepresentRulesSideEffect.LoadingBar(true))
            runCatching {
                updateRepresentRulesUseCase(
                    uiState.value.rules.filter { it.isRepresent }
                        .map { it.id }
                )
            }.onSuccess {
                _sideEffect.send(RepresentRulesSideEffect.LoadingBar(false))
                _sideEffect.send(RepresentRulesSideEffect.PopBackStack)
            }.onFailure { e ->
                Timber.e("updateRule() - onFailure() - e: ${e.stackTraceToString()}")
                if (e is HttpException) {
                    when (e.code()) {
                        LIMIT_RULES_CODE -> _sideEffect.send(RepresentRulesSideEffect.ShowLimitRulesToast)
                    }
                }
                _sideEffect.send(RepresentRulesSideEffect.LoadingBar(false))
            }
        }
    }

    private fun canAddRepresentRule(): Boolean {
        return uiState.value.rules.filter { it.isRepresent }.size < 3
    }

    private companion object {
        const val LIMIT_RULES_CODE = 403
    }
}

sealed class RepresentRulesSideEffect {
    object IDLE : RepresentRulesSideEffect()
    data class LoadingBar(val isLoading: Boolean) : RepresentRulesSideEffect()
    object ShowLimitRulesToast : RepresentRulesSideEffect()
    object PopBackStack : RepresentRulesSideEffect()
}

data class RepresentRulesState(
    val originRules: List<RepresentRuleUiModel> = emptyList(),
    val rules: List<RepresentRuleUiModel> = emptyList()
)

sealed class RepresentRulesEvent {
    data class FetchRules(val rules: List<Rule>) : RepresentRulesEvent()
    data class UpdateRule(val id: Int) : RepresentRulesEvent()
}

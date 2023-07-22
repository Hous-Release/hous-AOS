package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.usecase.rule.GetMainRulesUseCase
import hous.release.domain.usecase.search.SearchRuleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainRuleViewModel @Inject constructor(
    private val getMainRulesUseCase: GetMainRulesUseCase,
    private val searcher: SearchRuleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainRules())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMainRules()
    }

    fun fetchMainRules() {
        viewModelScope.launch {
            runCatching { getMainRulesUseCase() }
                .onSuccess { rules ->
                    _uiState.update {
                        MainRules(
                            originRules = rules,
                            filteredRules = rules
                        )
                    }
                }
                .onFailure {
                    Timber.e(it.stackTraceToString())
                }
        }
    }

    fun searchRule(searchQuery: String) {
        _uiState.update { state ->
            state.copy(
                searchQuery = searchQuery,
                filteredRules = searcher(searchQuery, state.originRules)
            )
        }
    }
}

data class MainRules(
    val originRules: List<MainRule> = emptyList(),
    val filteredRules: List<MainRule> = emptyList(),
    val searchQuery: String = ""
)

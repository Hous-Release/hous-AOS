package hous.release.android.presentation.our_rules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.usecase.rule.GetDetailRuleUseCase
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
    private val getDetailRuleUseCase: GetDetailRuleUseCase,
    private val searcher: SearchRuleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainRules())
    val uiState = _uiState.asStateFlow()

    init {
        fetchMainRules()
    }

    fun fetchDetailRule(id: Int) {
        viewModelScope.launch {
            runCatching { getDetailRuleUseCase(id) }
                .onSuccess { detailRule ->
                    _uiState.update { state ->
                        state.copy(detailRule = detailRule.toUiModel())
                    }
                }
                .onFailure {
                    Timber.e(it.stackTraceToString())
                }
        }
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

    private fun DetailRule.toUiModel() = DetailRuleUiModel(
        id = id,
        name = name,
        description = description,
        images = images,
        updatedAt = updatedAt
    )
}

data class MainRules(
    val detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    val originRules: List<MainRule> = emptyList(),
    val filteredRules: List<MainRule> = emptyList(),
    val searchQuery: String = ""
)

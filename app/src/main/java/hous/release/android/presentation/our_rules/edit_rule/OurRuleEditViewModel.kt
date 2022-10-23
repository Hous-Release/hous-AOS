package hous.release.android.presentation.our_rules.edit_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.SaveButtonState
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.usecase.GetOurRulesUseCase
import hous.release.domain.usecase.PutEditOurRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRuleEditViewModel @Inject constructor(
    private val getOurRulesUseCase: GetOurRulesUseCase,
    private val putEditOurRulesUseCase: PutEditOurRulesUseCase
) : ViewModel() {
    private var _uiState = MutableStateFlow(OurRuleEditUIState())
    val uiState: StateFlow<OurRuleEditUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getOurRulesUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success ->
                        _uiState.update { uiState ->
                            val ourRuleList = initRuleList(apiResult.data)
                            uiState.copy(isLoading = false, ourOriginalRuleList = ourRuleList, ourEditRuleList = ourRuleList)
                        }
                    is ApiResult.Empty -> {
                        // TODO EMPTY 뷰 나오면 ERROR 로직 처리해주기
                        _uiState.update { uiState ->
                            uiState.copy(isEmpty = true, isLoading = false)
                        }
                    }
                    is ApiResult.Error -> {
                        _uiState.update { uiState ->
                            uiState.copy(isError = true, isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun initRuleList(ruleList: List<OurRule>) = ruleList
        .mapIndexed { idx, ourRule ->
            when (idx) {
                in 0..1 -> ourRule.copy(ruleType = OurRule.RuleType.RepresentativeRule())
                2 -> ourRule.copy(ruleType = OurRule.RuleType.RepresentativeRule(false))
                else -> ourRule.copy(ruleType = OurRule.RuleType.GeneralRule())
            }
        }

    fun updateEditRuleList(editList: List<OurRule>) {
        _uiState.update { uiState ->
            uiState.copy(ourEditRuleList = initRuleList(editList))
        }
        Timber.e(uiState.value.ourEditRuleList.toString())
    }

    fun isChangeRuleList(): Boolean {
        val ourEditRuleList = uiState.value.ourEditRuleList
        uiState.value.ourOriginalRuleList.forEachIndexed { index, ourRule ->
            if (ourEditRuleList[index].id != ourRule.id) return true
        }
        return false
    }

    fun setSaveButtonState(saveButtonState: SaveButtonState) {
        _uiState.update { uiState ->
            uiState.copy(saveButtonState = saveButtonState)
        }
    }

    fun putOurEditRules() =
        viewModelScope.launch {
            putEditOurRulesUseCase(editedRules = uiState.value.ourEditRuleList).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> Timber.i(apiResult.data)
                    is ApiResult.Error -> Timber.e(apiResult.throwable)
                    is ApiResult.Empty -> Timber.e("IllegalArgument Exception")
                }
            }
        }

    data class OurRuleEditUIState(
        val ourOriginalRuleList: List<OurRule> = emptyList(),
        val ourEditRuleList: List<OurRule> = emptyList(),
        val isError: Boolean = false,
        val isEmpty: Boolean = false,
        val isLoading: Boolean = true,
        val saveButtonState: SaveButtonState = SaveButtonState.INACTIVE
    )
}

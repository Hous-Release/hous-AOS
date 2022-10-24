package hous.release.android.presentation.our_rules.edit_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.SaveButtonState
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.RuleType
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
                            val ourRuleList = initEditRuleList(apiResult.data)
                            uiState.copy(
                                isEmpty = false,
                                isLoading = false,
                                originalEditRuleList = ourRuleList,
                                editRuleList = ourRuleList
                            )
                        }
                    is ApiResult.Empty -> {
                        _uiState.update { uiState ->
                            uiState.copy(isLoading = false)
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

    private fun initEditRuleList(ruleList: List<OurRule>) = ruleList
        .mapIndexed { idx, ourRule ->
            when (idx) {
                0 -> ourRule.copy(ruleType = RuleType.REPRESENTATVIE_TOP)
                1 -> ourRule.copy(ruleType = RuleType.REPRESENTATVIE_MIDDLE)
                2 -> ourRule.copy(ruleType = RuleType.REPRESENTATVIE_BOTTOM)
                else -> ourRule.copy(ruleType = RuleType.GENERAL)
            }
        }

    fun updateEditRuleList(editList: List<OurRule>) {
        _uiState.update { uiState ->
            uiState.copy(editRuleList = initEditRuleList(editList))
        }
    }

    fun isChangeRuleList(): Boolean {
        val ourEditRuleList = uiState.value.editRuleList
        uiState.value.originalEditRuleList.forEachIndexed { index, ourRule ->
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
            putEditOurRulesUseCase(editedRules = uiState.value.editRuleList).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> Timber.i(apiResult.data)
                    is ApiResult.Error -> Timber.e(apiResult.msg)
                    is ApiResult.Empty -> Timber.e("IllegalArgument Exception")
                }
            }
        }

    data class OurRuleEditUIState(
        val originalEditRuleList: List<OurRule> = emptyList(),
        val editRuleList: List<OurRule> = emptyList(),
        val isError: Boolean = false,
        val isEmpty: Boolean = true,
        val isLoading: Boolean = true,
        val saveButtonState: SaveButtonState = SaveButtonState.INACTIVE
    )
}

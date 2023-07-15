package hous.release.android.presentation.our_rules.delete_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.util.ApiResult
import hous.release.domain.entity.rule.OurRule
import hous.release.domain.usecase.DeleteOurRulesUseCase
import hous.release.domain.usecase.GetOurRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRuleDeleteViewModel @Inject constructor(
    private val getOurRulesUseCase: GetOurRulesUseCase,
    private val deleteOurRulesUseCase: DeleteOurRulesUseCase
) :
    ViewModel() {
    private var _uiState = MutableStateFlow(OurRuleDeleteUIState())
    val uiState: StateFlow<OurRuleDeleteUIState> = _uiState.asStateFlow()
    private val mRulesIdHashMap = mutableMapOf<Int, Boolean>()

    init {
        viewModelScope.launch {
            getOurRulesUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _uiState.update { uiState ->
                            uiState.copy(
                                isEmpty = false,
                                isLoading = false,
                                ruleList = apiResult.data
                            )
                        }
                        apiResult.data.forEach { ourRule -> mRulesIdHashMap[ourRule.id] = false }
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

    fun updateDeleteRules(id: Int) {
        mRulesIdHashMap[id] = !requireNotNull(mRulesIdHashMap[id])
        if (requireNotNull(mRulesIdHashMap[id])) {
            _uiState.update { uiState ->
                uiState.copy(deleteRuleSize = uiState.deleteRuleSize + 1)
            }
            return
        }
        _uiState.update { uiState ->
            uiState.copy(deleteRuleSize = uiState.deleteRuleSize - 1)
        }
    }

    private fun getDeleteIdList() = mRulesIdHashMap.filterValues { checked ->
        checked
    }.map { entry -> entry.key }

    fun deleteRules() =
        viewModelScope.launch {
            val deleteRulesId: List<Int> = getDeleteIdList()
            deleteOurRulesUseCase(deleteRulesId).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> Timber.i(apiResult.data)
                    is ApiResult.Error -> Timber.e(apiResult.msg)
                    is ApiResult.Empty -> Timber.e("IllegalArgument Exception")
                }
            }
        }

    fun isActiveDeleteButton() = (uiState.value.deleteButtonState == ButtonState.ACTIVE)
    fun isDeleteRulesNotEmpty() = (uiState.value.deleteRuleSize != 0)
    fun setDeleteButtonState(deleteButtonState: ButtonState) {
        _uiState.update { uiState ->
            uiState.copy(deleteButtonState = deleteButtonState)
        }
    }

    data class OurRuleDeleteUIState(
        val deleteRuleSize: Int = 0,
        val ruleList: List<OurRule> = emptyList(),
        val isError: Boolean = false,
        val isEmpty: Boolean = true,
        val isLoading: Boolean = true,
        val deleteButtonState: ButtonState = ButtonState.INACTIVE
    )
}

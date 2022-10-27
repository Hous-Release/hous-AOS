package hous.release.android.presentation.our_rules.add_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.usecase.GetOurRulesUseCase
import hous.release.domain.usecase.PostAddRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRuleAddViewModel @Inject constructor(
    private val ourRulesUseCase: GetOurRulesUseCase,
    private val postAddRulesUseCase: PostAddRulesUseCase
) :
    ViewModel() {
    private var tmpId = -1
    var inputRuleNameField = MutableStateFlow<String>("")
    private var _uiState = MutableStateFlow(OurRuleAddUIState())
    val uiState: StateFlow<OurRuleAddUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ourRulesUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success ->
                        _uiState.update { uiState ->
                            uiState.copy(isLoading = false, ourRuleList = apiResult.data)
                        }
                    is ApiResult.Empty -> {
                        _uiState.update { uiState ->
                            uiState.copy(isEmpty = true, isLoading = false)
                        }
                    }
                    is ApiResult.Error -> {
                        // TODO ERROR 뷰 나오면 ERROR 로직 처리해주기
                        _uiState.update { uiState ->
                            uiState.copy(isError = true, isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun initInputRuleNameField() {
        inputRuleNameField.value = ""
    }
    fun isActiveSaveButton() = (uiState.value.saveButtonState == ButtonState.ACTIVE)

    fun setSaveButtonState(saveButtonState: ButtonState) {
        _uiState.value = uiState.value.copy(
            saveButtonState = saveButtonState
        )
    }

    fun addRule() {
        if (inputRuleNameField.value.isNotBlank()) {
            _uiState.value =
                uiState.value.copy(
                    ourRuleList = _uiState.value.ourRuleList + listOf(
                        OurRule(
                            tmpId--,
                            inputRuleNameField.value
                        )
                    ),
                    addedRuleList = _uiState.value.addedRuleList + listOf(
                        inputRuleNameField.value
                    )
                )
            initInputRuleNameField()
        }
    }

    fun putAddRuleList() =
        viewModelScope.launch {
            postAddRulesUseCase(uiState.value.addedRuleList)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Success -> Timber.i(apiResult.data)
                        is ApiResult.Error -> Timber.e(apiResult.msg)
                        is ApiResult.Empty -> Timber.e("IllegalArgument Exception")
                    }
                }
        }

    data class OurRuleAddUIState(
        val ourRuleList: List<OurRule> = emptyList(),
        val addedRuleList: List<String> = emptyList(),
        val isError: Boolean = false,
        val isEmpty: Boolean = false,
        val isLoading: Boolean = true,
        val saveButtonState: ButtonState = ButtonState.INACTIVE
    )
}

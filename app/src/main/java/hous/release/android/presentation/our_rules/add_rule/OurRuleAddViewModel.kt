package hous.release.android.presentation.our_rules.add_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.usecase.GetOurRulesUseCase
import hous.release.domain.usecase.PostAddRulesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRuleAddViewModel @Inject constructor(
    private val ourRulesUseCase: GetOurRulesUseCase,
    private val postAddRulesUseCase: PostAddRulesUseCase
) : ViewModel() {
    private var tmpId = -1
    var inputRuleNameField = MutableStateFlow<String>("")
    private var _uiState = MutableStateFlow(OurRuleAddUIState())
    val uiState: StateFlow<OurRuleAddUIState> = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<OurRuleAddEvent>()
    val uiEvent: SharedFlow<OurRuleAddEvent> = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            ourRulesUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> _uiState.update { uiState ->
                        uiState.copy(isLoading = false, ourRuleList = apiResult.data)
                    }
                    is ApiResult.Empty -> {
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
            _uiState.value = uiState.value.copy(
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

    fun addRuleList() = viewModelScope.launch {
        when (val responseCode = postAddRulesUseCase(uiState.value.addedRuleList)) {
            SUCCEES_CODE -> _uiEvent.emit(OurRuleAddEvent.AddSuccess)
            DUPLICATE_ERROR_CODE -> _uiEvent.emit(OurRuleAddEvent.DuplicateError)
            else -> Timber.d("Error $responseCode")
        }
    }

    companion object {
        private const val SUCCEES_CODE = 201
        private const val DUPLICATE_ERROR_CODE = 409
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

sealed class OurRuleAddEvent {
    object DuplicateError : OurRuleAddEvent()
    object AddSuccess : OurRuleAddEvent()
}

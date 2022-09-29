package hous.release.android.presentation.our_rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.OurRule
import hous.release.domain.usecase.OurRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRulesViewModel @Inject constructor(private val ourRulesUseCase: OurRulesUseCase) :
    ViewModel() {
    private var _uiState = MutableStateFlow(OurRulesUiState())
    val uiState: StateFlow<OurRulesUiState> = _uiState.asStateFlow()

    fun getOurRulesInfo() {
        viewModelScope.launch {
            ourRulesUseCase().collect() { ourRulesContent ->
                if (!ourRulesContent.errorMsg.isNullOrBlank()) {
                    Timber.e("ourRulesUseCase error msg - ${ourRulesContent.errorMsg}")
                    _uiState.value = OurRulesUiState().copy(
                        isLoading = false,
                        isError = true
                    )
                    return@collect
                }
                _uiState.value = OurRulesUiState().copy(
                    isLoading = false,
                    isError = false,
                    isEmptyGeneralRuleList = ourRulesContent.isEmptyGeneralRuleList,
                    isEmptyRepresentativeRuleList = ourRulesContent.isEmptyRepresentativeRuleList,
                    ourRuleList = ourRulesContent.ourRuleList
                )
            }
        }
    }

    data class OurRulesUiState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isEmptyRepresentativeRuleList: Boolean = true,
        val isEmptyGeneralRuleList: Boolean = true,
        val ourRuleList: List<OurRule> = emptyList()
    )
}

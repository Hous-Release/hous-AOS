package hous.release.android.presentation.our_rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OurRulesViewModel @Inject constructor(private val ourRulesRepository: OurRulesRepository) :
    ViewModel() {
    private var _uiState = MutableStateFlow(OurRulesUiState())
    val uiState: StateFlow<OurRulesUiState> = _uiState.asStateFlow()

    fun getOurRulesInfo() {
        viewModelScope.launch {
            ourRulesRepository.fetchOurRulesContent().collect { ourRulesContent ->
                if (ourRulesContent.errorState) {
                    // TODO error뷰 처리
                    return@collect
                }
                _uiState.value = OurRulesUiState().copy(
                    isEmptyGeneralRuleList = ourRulesContent.isEmptyGeneralRuleList,
                    isEmptyRepresentativeRuleList = ourRulesContent.isEmptyRepresentativeRuleList,
                    ourRuleList = ourRulesContent.ourRuleList
                )
            }
        }
    }

    data class OurRulesUiState(
        val isEmptyRepresentativeRuleList: Boolean = true,
        val isEmptyGeneralRuleList: Boolean = true,
        val ourRuleList: List<OurRule> = emptyList()
    )
}

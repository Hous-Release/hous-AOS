package hous.release.android.presentation.our_rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.OurRuleEntity
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
            ourRulesRepository.fetchOurRulesContent().collect { ourRuleList ->
                val rules: List<OurRuleEntity> =
                    ourRuleList.map { ourRule -> ourRule.toOurRuleEntity() }

                // 만약 rules.isEmpty() 인 경우 : Exception이 뜨거나 서버에서 emptyList 보내줄 경우
                if (rules.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        ourRuleList = defaultRuleList,
                        isEmptyRepresentativeRuleList = true,
                        isEmptyGeneralRuleList = true
                    )
                } else if (rules.size <= 3) {
                    val tmpRuleList = _uiState.value.ourRuleList.toMutableList()
                    rules.forEachIndexed { idx, value ->
                        tmpRuleList[idx] = value
                    }
                    _uiState.value = _uiState.value.copy(
                        ourRuleList = tmpRuleList,
                        isEmptyRepresentativeRuleList = false,
                        isEmptyGeneralRuleList = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        ourRuleList = rules,
                        isEmptyRepresentativeRuleList = false,
                        isEmptyGeneralRuleList = false
                    )
                }
            }
        }
    }

    private fun OurRule.toOurRuleEntity() = OurRuleEntity(this.id, this.name)

    data class OurRulesUiState(
        val isEmptyRepresentativeRuleList: Boolean = true,
        val isEmptyGeneralRuleList: Boolean = true,
        val ourRuleList: List<OurRuleEntity> = defaultRuleList
    )

    companion object {
        private val defaultRuleList = listOf(
            OurRuleEntity(1111, ""),
            OurRuleEntity(2222, ""),
            OurRuleEntity(3333, "")
        )
    }
}

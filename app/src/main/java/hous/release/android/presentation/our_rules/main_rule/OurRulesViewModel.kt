package hous.release.android.presentation.our_rules.main_rule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.util.ApiResult
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.usecase.GetOurMainRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRulesViewModel @Inject constructor(
    private val ourMainRulesUseCase: GetOurMainRulesUseCase
) :
    ViewModel() {
    private var _uiState = MutableStateFlow(OurRulesUiState())
    val uiState: StateFlow<OurRulesUiState> = _uiState.asStateFlow()

    fun getOurRulesInfo() {
        viewModelScope.launch {
            ourMainRulesUseCase()
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Success -> {
                            val ourRulesContent = apiResult.data
                            _uiState.value = OurRulesUiState().copy(
                                isEmptyGeneralRuleList = ourRulesContent.isEmptyGeneralRuleList,
                                isEmptyRepresentativeRuleList = ourRulesContent.isEmptyRepresentativeRuleList,
                                ourRuleList = ourRulesContent.ourRuleList
                            )
                        }
                        is ApiResult.Empty -> {
                            _uiState.value = OurRulesUiState().copy()
                        }
                        is ApiResult.Error -> {
                            // TODO 추후에 error 뷰 로직 수정
                            Timber.e("ourRulesUseCase error msg - ${apiResult.msg}")
                            _uiState.value = OurRulesUiState().copy()
                        }
                    }
                }
        }
    }

    data class OurRulesUiState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val isEmptyRepresentativeRuleList: Boolean = true,
        val isEmptyGeneralRuleList: Boolean = true,
        val ourRuleList: List<MainRule> = emptyList()
    )
}

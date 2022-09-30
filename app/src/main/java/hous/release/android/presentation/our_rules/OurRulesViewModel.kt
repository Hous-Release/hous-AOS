package hous.release.android.presentation.our_rules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.usecase.FetchOurRulesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OurRulesViewModel @Inject constructor(private val ourRulesUseCase: FetchOurRulesUseCase) :
    ViewModel() {
    private var _uiState = MutableStateFlow(OurRulesUiState())
    val uiState: StateFlow<OurRulesUiState> = _uiState.asStateFlow()

    fun getOurRulesInfo() {
        viewModelScope.launch {
            ourRulesUseCase.fetchOurMainRules()
                .catch {
                    if (it is IndexOutOfBoundsException) {
                        Timber.e(it.message)
                        _uiState.value = OurRulesUiState().copy()
                    }
                    return@catch
                }
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
                            Timber.e("ourRulesUseCase error msg - ${apiResult.throwable}")
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
        val ourRuleList: List<OurRule> = emptyList()
    )
}

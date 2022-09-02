package hous.release.android.presentation.our_rules

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.RuleInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OurRulesViewModel @Inject constructor() : ViewModel() {
    private var _uiState = MutableStateFlow(OurRulesUiState())
    val uiState: StateFlow<OurRulesUiState>
        get() = _uiState.asStateFlow()
    private var _isEmptyRepresentativeRuleList = MutableStateFlow(true)
    val isEmptyRepresentativeRuleList: StateFlow<Boolean>
        get() = _isEmptyRepresentativeRuleList.asStateFlow()
    private var _isEmptyGeneralRuleList = MutableStateFlow(true)
    val isEmptyGeneralRuleList: StateFlow<Boolean>
        get() = _isEmptyGeneralRuleList.asStateFlow()

    fun getOurRulesInfo() {
        // 일단 dummy data로 (추후에 repository or usecase에서 받아오기)
        val rules: List<RuleInfo> =
            listOf(
                RuleInfo("1111", "우리 집 대장은 최코코"),
                RuleInfo("2222", "10시, 18시 코코님 밥 챙겨드리기"),
                RuleInfo("3333", "자기가 먹은 건 자기가 치우기!"),
                RuleInfo("4444", "아침에 일어나면 이불 정리하기"),
                RuleInfo("5555", "밤 10시 지나면 이어폰 끼기"),
                RuleInfo("11412311", "우리 집 대장은 최코코"),
                RuleInfo("4444", "아침에 일어나면 이불 정리하기"),
                RuleInfo("512355", "밤 10시 지나면 이어폰 끼기"),
                RuleInfo("612366", "냄새나는 음식  먹고나서 환기하기"),
                RuleInfo("7721377", "맛있는 식당 찾으면 공유하기"),
                RuleInfo("112311", "우리 집 대장은 최코코"),
                RuleInfo("2221322", "10시, 18시 코코님 밥 챙겨드리기"),
                RuleInfo("3331233", "자기가 먹은 건 자기가 치우기!"),
                RuleInfo("4412344", "아침에 일어나면 이불 정리하기"),
                RuleInfo("5552135", "밤 10시 지나면 이어폰 끼기"),
                RuleInfo("6612366", "냄새나는 음식  먹고나서 환기하기"),
                RuleInfo("7771237", "맛있는 식당 찾으면 공유하기")
            )
        if (rules.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                ourRuleList = defaultRuleList
            )
            _isEmptyRepresentativeRuleList.value = true
            _isEmptyGeneralRuleList.value = true
        } else if (rules.size <= 3) {
            val tmpRuleList = _uiState.value.ourRuleList.toMutableList()
            rules.forEachIndexed { idx, value ->
                tmpRuleList[idx] = value
            }
            _uiState.value = _uiState.value.copy(
                ourRuleList = tmpRuleList.toList()
            )
            _isEmptyRepresentativeRuleList.value = false
            _isEmptyGeneralRuleList.value = true
        } else {
            _uiState.value = _uiState.value.copy(
                ourRuleList = rules
            )
            _isEmptyRepresentativeRuleList.value = false
            _isEmptyGeneralRuleList.value = false
        }
    }

    data class OurRulesUiState(
        val ourRuleList: List<RuleInfo> = defaultRuleList
    )

    companion object {
        private val defaultRuleList = listOf(
            RuleInfo("1111", ""),
            RuleInfo("2222", ""),
            RuleInfo("3333", "")
        )
    }
}
// dummyData
// listOf(
// RuleInfo("1111", "우리 집 대장은 최코코"),
// RuleInfo("2222", "10시, 18시 코코님 밥 챙겨드리기"),
// RuleInfo("3333", "자기가 먹은 건 자기가 치우기!"),
// RuleInfo("4444", "아침에 일어나면 이불 정리하기"),
// RuleInfo("5555", "밤 10시 지나면 이어폰 끼기"),
// RuleInfo("11412311", "우리 집 대장은 최코코"),
// RuleInfo("4444", "아침에 일어나면 이불 정리하기"),
// RuleInfo("512355", "밤 10시 지나면 이어폰 끼기"),
// RuleInfo("612366", "냄새나는 음식  먹고나서 환기하기"),
// RuleInfo("7721377", "맛있는 식당 찾으면 공유하기"),
// RuleInfo("112311", "우리 집 대장은 최코코"),
// RuleInfo("2221322", "10시, 18시 코코님 밥 챙겨드리기"),
// RuleInfo("3331233", "자기가 먹은 건 자기가 치우기!"),
// RuleInfo("4412344", "아침에 일어나면 이불 정리하기"),
// RuleInfo("5552135", "밤 10시 지나면 이어폰 끼기"),
// RuleInfo("6612366", "냄새나는 음식  먹고나서 환기하기"),
// RuleInfo("7771237", "맛있는 식당 찾으면 공유하기")
//        )

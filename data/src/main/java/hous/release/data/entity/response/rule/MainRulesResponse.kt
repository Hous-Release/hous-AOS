package hous.release.data.entity.response.rule

import hous.release.domain.entity.rule.MainRule
import hous.release.domain.entity.rule.type.RuleType

data class MainRulesResponse(
    val rules: List<MainRuleResponse> = emptyList()
)

data class MainRuleResponse(
    val id: Int = -1,
    val name: String = "",
    val createdAt: String = "",
    val isNew: Boolean = false
) {
    fun toOurRule() = MainRule(
        id = id,
        name = name,
        ruleType = RuleType.GENERAL
    )
}

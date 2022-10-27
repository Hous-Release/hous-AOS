package hous.release.domain.entity.response

import hous.release.domain.entity.RuleType

data class OurRule(
    val id: Int = -1,
    val name: String = "",
    val ruleType: RuleType = RuleType.GENERAL
)

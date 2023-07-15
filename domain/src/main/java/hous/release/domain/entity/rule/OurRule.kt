package hous.release.domain.entity.rule

import hous.release.domain.entity.rule.type.RuleType

data class OurRule(
    val id: Int = NO_ID,
    val name: String = NO_NAME,
    val ruleType: RuleType = RuleType.GENERAL
) {
    companion object {
        private const val NO_NAME = "다른 Rule도 추가해보세요!"
        private const val NO_ID = -1
    }
}

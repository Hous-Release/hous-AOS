package hous.release.domain.entity.rule

data class OurRulesContent(
    val ourRuleList: List<MainRule> = defaultRuleList,
    val isEmptyRepresentativeRuleList: Boolean = true,
    val isEmptyGeneralRuleList: Boolean = true
) {
    companion object {
        val defaultRuleList = listOf(
            MainRule(),
            MainRule(),
            MainRule()
        )
    }
}

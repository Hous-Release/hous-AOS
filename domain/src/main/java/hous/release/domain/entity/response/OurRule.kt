package hous.release.domain.entity.response

data class OurRule(
    val id: Int = -1,
    val name: String = "",
    val ruleType: RuleType = RuleType.GeneralRule()
) {
    sealed class RuleType {
        data class RepresentativeRule(val hasDivider: Boolean = true) : RuleType()
        data class GeneralRule(val hasDivider: Boolean = true) : RuleType()
    }

    fun toNoDividerRule(ruleType: RuleType) = when (ruleType) {
        is RuleType.RepresentativeRule -> copy(ruleType = RuleType.RepresentativeRule(false))
        is RuleType.GeneralRule -> copy(ruleType = RuleType.RepresentativeRule(false))
    }

    fun toDividerRule(ruleType: RuleType) = when (ruleType) {
        is RuleType.RepresentativeRule -> copy(ruleType = RuleType.RepresentativeRule(true))
        is RuleType.GeneralRule -> copy(ruleType = RuleType.RepresentativeRule(true))
    }
}

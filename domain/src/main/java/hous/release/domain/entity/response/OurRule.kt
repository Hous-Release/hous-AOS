package hous.release.domain.entity.response

data class OurRule(
    val id: Int = -1,
    val name: String = "",
    val ruleType: RuleType = RuleType.GeneralRule
) {
    sealed class RuleType {
        data class RepresentativeRule(val hasDivider: Boolean = true) : RuleType()
        object GeneralRule : RuleType()
    }
}

package hous.release.android.presentation.our_rules.model

import hous.release.domain.entity.rule.Rule

@Suppress("DataClassPrivateConstructor")
data class RepresentRuleUiModel private constructor(
    val id: Int,
    val name: String,
    val isRepresent: Boolean
) {
    companion object {
        fun from(rule: Rule) = RepresentRuleUiModel(
            rule.id,
            rule.name,
            rule.isRepresent
        )
    }
}

package hous.release.data.entity.response.rule

import hous.release.domain.entity.rule.Rule

data class RulesResponse(
    val rules: List<RuleResponse> = emptyList()
)

data class RuleResponse(
    val id: Int = -1,
    val name: String = "",
    val isRepresent: Boolean = false,
    val createdAt: String = "",
    val isNew: Boolean = false
) {
    fun toRule() = Rule(
        id = id,
        name = name,
        isRepresent = isRepresent,
        createdAt = createdAt.substringBefore('T').replace('-', '.'),
        isNew = isNew
    )
}

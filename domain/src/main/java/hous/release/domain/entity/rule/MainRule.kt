package hous.release.domain.entity.rule

import hous.release.domain.entity.BaseRule

data class MainRule(
    override val id: Int = NO_ID,
    override val name: String = NO_NAME,
    val isRepresent: Boolean = false,
    val createdAt: String = "",
    val isNew: Boolean = false
) : BaseRule(id, name) {
    companion object {
        private const val NO_NAME = "다른 Rule도 추가해보세요!"
        private const val NO_ID = -1
    }
}

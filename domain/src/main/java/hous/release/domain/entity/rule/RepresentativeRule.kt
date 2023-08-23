package hous.release.domain.entity.rule

import hous.release.domain.entity.Rule

data class RepresentativeRule(
    override val id: Int = NO_ID,
    override val name: String = NO_NAME,
    val isRepresent: Boolean = false
) : Rule(id, name) {

    private companion object {
        private const val NO_NAME = "제목없음"
        private const val NO_ID = -1
    }
}

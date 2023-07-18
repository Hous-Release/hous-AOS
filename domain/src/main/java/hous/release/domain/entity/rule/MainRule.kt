package hous.release.domain.entity.rule

data class MainRule(
    val id: Int = NO_ID,
    val name: String = NO_NAME,
    val createdAt: String = "",
    val isNew: Boolean = false
) {
    companion object {
        private const val NO_NAME = "다른 Rule도 추가해보세요!"
        private const val NO_ID = -1
    }
}

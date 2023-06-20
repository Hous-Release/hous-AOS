package hous.release.domain.entity

data class TodoDetail(
    val todoId: Int = NO_ID,
    val name: String,
    val selectedUsers: List<User>,
    val dayOfWeeks: String
) {
    data class User(
        val onboardingId: Int,
        val color: HomyType,
        val nickname: String
    )

    companion object {
        private const val NO_ID = 0
    }
}

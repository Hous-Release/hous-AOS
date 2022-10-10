package hous.release.domain.entity

data class TodoDetail(
    val name: String,
    val selectedUsers: List<User>,
    val dayOfWeeks: String
) {
    data class User(
        val onboardingId: Int,
        val color: HomyType,
        val nickname: String
    )
}

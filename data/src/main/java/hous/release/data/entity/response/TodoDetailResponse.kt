package hous.release.data.entity.response

import hous.release.domain.entity.TodoDetail

data class TodoDetailResponse(
    val name: String,
    val selectedUsers: List<UserEntity>,
    val dayOfWeeks: String
) {
    data class UserEntity(
        val onboardingId: Int,
        val color: String,
        val nickname: String
    ) {
        fun toUser() = TodoDetail.User(
            onboardingId = onboardingId,
            color = color,
            nickname = nickname
        )
    }

    fun toTodoDetail() = TodoDetail(
        name = name,
        selectedUsers = selectedUsers.map { user -> user.toUser() },
        dayOfWeeks = dayOfWeeks
    )
}

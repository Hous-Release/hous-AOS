package hous.release.data.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser

data class ToDoUsersResponse(
    val users: List<ToDoUserResponse> = emptyList()
) {
    data class ToDoUserResponse(
        val color: String = "GRAY",
        val nickname: String = "",
        val onboardingId: Int = 0
    ) {
        fun toToDoUser() = ToDoUser().copy(
            homyType = HomyType.valueOf(color),
            nickname = nickname,
            onBoardingId = onboardingId
        )
    }
}

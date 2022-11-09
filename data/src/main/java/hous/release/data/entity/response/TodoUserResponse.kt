package hous.release.data.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser

data class TodoUserResponse(
    val color: String = NO_COLOR,
    val dayOfWeeks: List<String> = emptyList(),
    val isSelected: Boolean = false,
    val nickname: String = "",
    val onboardingId: Int = NO_ID
) {
    fun toTodoUser() = ToDoUser(
        homyType = HomyType.valueOf(color),
        nickname = nickname,
        isChecked = isSelected,
        onBoardingId = onboardingId,
        dayOfWeeks = transformDayOfWeeks(dayOfWeeks)
    )

    private fun transformDayOfWeeks(dayOfWeeks: List<String>): List<Boolean> {
        val newDayOfWeeks = MutableList(7) { false }
        dayOfWeeks.forEach { day -> newDayOfWeeks[requireNotNull(dayIndexMap[day])] = true }
        return newDayOfWeeks
    }

    companion object {
        private const val NO_COLOR = "GRAY"
        private const val NO_ID = -1
        private val dayIndexMap = hashMapOf(
            "MONDAY" to 0,
            "TUESDAY" to 1,
            "WEDNESDAY" to 2,
            "THURSDAY" to 3,
            "FRIDAY" to 4,
            "SATURDAY" to 5,
            "SUNDAY" to 6
        )
    }
}

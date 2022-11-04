package hous.release.domain.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.UpdateToDoUser

data class ToDoUser(
    val homyType: HomyType = HomyType.GRAY,
    val nickname: String = "",
    val onBoardingId: Int = -1,
    val dayOfWeeks: List<Boolean> = List(7) { false },
    val isChecked: Boolean = false
) {

    fun toAddedToDoUser() =
        UpdateToDoUser(dayOfWeeks = transformDayOfWeeks(dayOfWeeks), onboardingId = onBoardingId)

    private fun transformDayOfWeeks(dayOfWeeks: List<Boolean>): List<String> {
        val newDayOfWeeks = mutableListOf<String>()
        dayOfWeeks.forEachIndexed { idx, isChecked ->
            if (isChecked) newDayOfWeeks.add(
                dayTable[idx]
            )
        }
        return newDayOfWeeks
    }

    companion object {
        private val dayTable = listOf(
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
        )
    }
}

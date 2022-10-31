package hous.release.domain.entity.response

import hous.release.domain.entity.HomyType

data class ToDoUser(
    val homyType: HomyType = HomyType.GRAY,
    val nickname: String = "",
    val onBoardingId: Int = -1,
    val dayOfWeeks: List<Boolean> = List(7) { false },
    val isChecked: Boolean = false
)

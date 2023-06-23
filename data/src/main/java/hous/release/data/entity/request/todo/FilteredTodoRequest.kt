package hous.release.data.entity.request.todo

data class FilteredTodoRequest(
    val dayOfWeeks: List<String>?,
    val onboardingIds: List<Int>?
) {
    companion object {
        fun instanceOf(
            dayOfWeeks: List<String>?,
            onboardingIds: List<Int>?
        ): FilteredTodoRequest =
            FilteredTodoRequest(dayOfWeeks?.map { transformDayOfWeek(it) }, onboardingIds)

        private fun transformDayOfWeek(dayOfWeek: String): String = when (dayOfWeek) {
            "월" -> "MONDAY"
            "화" -> "TUESDAY"
            "수" -> "WEDNESDAY"
            "목" -> "THURSDAY"
            "금" -> "FRIDAY"
            "토" -> "SATURDAY"
            else -> "SUNDAY"
        }
    }
}

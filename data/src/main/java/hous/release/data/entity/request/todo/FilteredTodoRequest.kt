package hous.release.data.entity.request.todo

data class FilteredTodoRequest(
    val dayOfWeeks: List<String>?,
    val onboardingIds: List<Int>?
) {
    fun newInstance() = FilteredTodoRequest(
        dayOfWeeks = dayOfWeeks?.map { transformDayOfWeek(it) },
        onboardingIds = onboardingIds
    )

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

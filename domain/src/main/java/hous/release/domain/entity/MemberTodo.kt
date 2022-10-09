package hous.release.domain.entity

data class MemberTodo(
    val dayOfWeek: String,
    val todoCnt: Int,
    val dayOfWeekTodos: List<Todo>
)

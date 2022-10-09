package hous.release.domain.entity

data class MemberTodo(
    val dayOfWeek: Int,
    val todoCnt: Int,
    val dayOfWeekTodos: List<Todo>
)

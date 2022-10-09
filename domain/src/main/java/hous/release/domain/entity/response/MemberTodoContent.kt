package hous.release.domain.entity.response

import hous.release.domain.entity.MemberTodo

data class MemberTodoContent(
    val color: String,
    val dayOfWeekTodos: List<MemberTodo>,
    val totalTodoCnt: Int,
    val userName: String
)

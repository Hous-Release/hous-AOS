package hous.release.data.entity.response

import hous.release.domain.entity.response.AllMemberTodo

data class AllMemberTodoResponse(
    val todos: List<MemberTodoResponse>,
    val totalRoomTodoCnt: Int
) {
    fun toAllMemberTodo(): AllMemberTodo = AllMemberTodo(
        todos = todos.map { it.toMemberTodoContent() },
        totalRoomTodoCnt = totalRoomTodoCnt
    )
}

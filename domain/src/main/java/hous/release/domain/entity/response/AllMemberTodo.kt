package hous.release.domain.entity.response

data class AllMemberTodo(
    val todos: List<MemberTodoContent>,
    val totalRoomTodoCnt: Int
)

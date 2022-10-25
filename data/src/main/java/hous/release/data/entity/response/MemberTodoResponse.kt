package hous.release.data.entity.response

import hous.release.data.entity.ToDoEntity
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.MemberTodo
import hous.release.domain.entity.response.MemberTodoContent

data class MemberTodoResponse(
    val color: String,
    val dayOfWeekTodos: List<MemberTodoEntity>,
    val totalTodoCnt: Int,
    val userName: String
) {
    data class MemberTodoEntity(
        val dayOfWeek: String,
        val todoCnt: Int,
        val dayOfWeekTodos: List<ToDoEntity>
    ) {
        fun toMemberTodo() = MemberTodo(
            dayOfWeek = dayOfWeek,
            todoCnt = todoCnt,
            dayOfWeekTodos = dayOfWeekTodos.map { todo -> todo.toTodo() }
        )
    }

    fun toMemberTodoContent() = MemberTodoContent(
        color = HomyType.valueOf(color),
        dayOfWeekTodos = dayOfWeekTodos
            .map { memberTodo -> memberTodo.toMemberTodo() }
            .filter { memberTodo -> memberTodo.todoCnt != 0 },
        totalTodoCnt = totalTodoCnt,
        userName = userName
    )
}

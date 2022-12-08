package hous.release.data.entity.response

import hous.release.domain.entity.response.DailyTodo

data class DailyTodoResponse(
    val todos: List<ToDoMainResponse>,
    val totalRoomTodoCnt: Int
) {
    fun toDailyTodo() = DailyTodo(
        todos = todos.map { it.toTodoMain() },
        totalRoomTodoCnt = totalRoomTodoCnt
    )
}

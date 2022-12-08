package hous.release.domain.entity.response

data class DailyTodo(
    val todos: List<TodoMain>,
    val totalRoomTodoCnt: Int
)

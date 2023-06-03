package hous.release.domain.entity.todo

data class FilteredTodo(
    val todos: List<TodoWithNew>,
    val todosCnt: Int
)

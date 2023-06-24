package hous.release.data.entity.response.todo

import hous.release.domain.entity.todo.FilteredTodo

data class FilteredTodoResponse(
    val todos: List<TodoWithNewResponse>,
    val todosCnt: Int
) {
    fun toFilteredTodo() = FilteredTodo(
        todos = todos.map { todo -> todo.toTodoWithNew() },
        todosCnt = todosCnt
    )
}

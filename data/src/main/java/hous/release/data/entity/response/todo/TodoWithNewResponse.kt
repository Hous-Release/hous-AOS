package hous.release.data.entity.response.todo

import hous.release.domain.entity.todo.TodoWithNew

data class TodoWithNewResponse(
    val todoId: Int,
    val isNew: Boolean,
    val todoName: String
) {
    fun toTodoWithNew() = TodoWithNew(
        id = todoId,
        isNew = isNew,
        name = todoName
    )
}

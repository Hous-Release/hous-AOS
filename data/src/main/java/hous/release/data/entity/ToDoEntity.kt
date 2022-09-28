package hous.release.data.entity

import hous.release.domain.entity.Todo

data class ToDoEntity(
    val isChecked: Boolean,
    val todoId: Int,
    val todoName: String,
    val nicknames: List<String>?,
    val status: String?
) {
    fun toTodo() = Todo(
        isChecked = isChecked,
        todoId = todoId,
        todoName = todoName,
        nicknames = nicknames ?: emptyList(),
        status = status ?: ""
    )
}

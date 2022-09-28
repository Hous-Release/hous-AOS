package hous.release.data.entity.response

import hous.release.data.entity.ToDoEntity
import hous.release.domain.entity.response.TodoMain

data class ToDoMainResponse(
    val date: String?,
    val dayOfWeek: String,
    val myTodos: List<ToDoEntity>,
    val myTodosCnt: Int?,
    val ourTodos: List<ToDoEntity>,
    val ourTodosCnt: Int?,
    val progress: Int?
) {
    fun toTodoMain() = TodoMain(
        date = date ?: "",
        dayOfWeek = dayOfWeek,
        myTodos = myTodos.map { todo -> todo.toTodo() },
        myTodosCnt = myTodosCnt ?: -1,
        ourTodos = ourTodos.map { todo -> todo.toTodo() },
        ourTodosCnt = ourTodosCnt ?: -1,
        progress = progress ?: 0
    )
}

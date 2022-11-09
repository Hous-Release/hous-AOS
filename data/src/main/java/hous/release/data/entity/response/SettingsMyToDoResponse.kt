package hous.release.data.entity.response

import hous.release.domain.entity.response.MyTodo
import hous.release.domain.entity.response.SettingsMyToDo

data class SettingsMyToDoResponse(
    val myTodos: List<MyTodoEntity>,
    val myTodosCnt: Int
) {
    fun toSettingsMyToDo(): SettingsMyToDo =
        SettingsMyToDo(
            myTodos = this.myTodos.map { todos -> MyTodo(todos.dayOfWeeks, todos.todoName) },
            myTodosCnt = this.myTodosCnt
        )
}

data class MyTodoEntity(
    val dayOfWeeks: String,
    val todoName: String
)

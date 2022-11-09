package hous.release.data.entity.response

import hous.release.domain.entity.response.MyToDo
import hous.release.domain.entity.response.SettingsMyToDo

data class SettingsMyToDoResponse(
    val myTodos: List<MyToDoEntity>,
    val myTodosCnt: Int
) {
    fun toSettingsMyToDo(): SettingsMyToDo =
        SettingsMyToDo(
            myTodos = this.myTodos.map { todos -> MyToDo(todos.dayOfWeeks, todos.todoName) },
            myTodosCnt = this.myTodosCnt
        )
}

data class MyToDoEntity(
    val dayOfWeeks: String,
    val todoName: String
)

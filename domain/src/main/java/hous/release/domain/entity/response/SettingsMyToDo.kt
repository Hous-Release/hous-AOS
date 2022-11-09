package hous.release.domain.entity.response

data class SettingsMyToDo(
    val myTodos: List<MyTodo>,
    val myTodosCnt: Int
)

data class MyTodo(
    val dayOfWeeks: String,
    val todoName: String
)

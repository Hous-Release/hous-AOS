package hous.release.domain.entity.response

data class SettingsMyToDo(
    val myTodos: List<MyToDo>,
    val myTodosCnt: Int
)

data class MyToDo(
    val dayOfWeeks: String,
    val todoName: String
)

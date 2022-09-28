package hous.release.domain.entity.response

import hous.release.domain.entity.Todo

data class TodoMain(
    val date: String,
    val dayOfWeek: String,
    val myTodos: List<Todo>,
    val myTodosCnt: Int,
    val ourTodos: List<Todo>,
    val ourTodosCnt: Int,
    val progress: Int
)

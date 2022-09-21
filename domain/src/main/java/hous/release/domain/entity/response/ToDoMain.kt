package hous.release.domain.entity.response

import hous.release.domain.entity.ToDo

interface ToDoMain {
    val date: String
    val dayOfWeek: String
    val myTodos: List<ToDo>
    val myTodosCnt: Int
    val ourTodos: List<ToDo>
    val ourTodosCnt: Int
    val progress: Int
}

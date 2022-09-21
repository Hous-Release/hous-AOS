package hous.release.data.entity.response

import hous.release.data.entity.ToDoEntity
import hous.release.domain.entity.response.ToDoMain

data class ToDoMainResponse(
    override val date: String,
    override val dayOfWeek: String,
    override val myTodos: List<ToDoEntity>,
    override val myTodosCnt: Int,
    override val ourTodos: List<ToDoEntity>,
    override val ourTodosCnt: Int,
    override val progress: Int
) : ToDoMain

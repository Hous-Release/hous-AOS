package hous.release.data.entity

import hous.release.domain.entity.ToDo

data class ToDoEntity(
    override val isChecked: Boolean,
    override val todoId: Int,
    override val todoName: String,
    override val nicknames: List<String>,
    override val status: String
) : ToDo

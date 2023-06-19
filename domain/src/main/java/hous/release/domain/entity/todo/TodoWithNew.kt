package hous.release.domain.entity.todo

import hous.release.domain.entity.Rule

data class TodoWithNew(
    val isNew: Boolean,
    override val id: Int,
    override val name: String
) : Rule(id, name)

package hous.release.domain.entity.todo

import hous.release.domain.entity.BaseRule

data class TodoWithNew(
    val isNew: Boolean,
    override val id: Int,
    override val name: String
) : BaseRule(id, name)

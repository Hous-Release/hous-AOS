package hous.release.domain.entity.todo

import hous.release.domain.entity.HomyType

data class Homy(
    val id: Int,
    val name: String,
    val homyType: HomyType
)
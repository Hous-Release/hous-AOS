package hous.release.domain.entity

data class MainTodo(
    val isNew: Boolean,
    override val id: Int,
    override val name: String
): Rule(id, name)

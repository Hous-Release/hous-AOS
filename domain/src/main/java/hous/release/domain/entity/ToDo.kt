package hous.release.domain.entity

data class ToDo(
    val isChecked: Boolean = false,
    val todoId: Int = 0,
    val todoName: String = "",
    val nicknames: List<String> = emptyList(),
    val status: String = ""
)

package hous.release.domain.entity

data class Todo(
    val isChecked: Boolean,
    val todoId: Int,
    val todoName: String,
    val nicknames: List<String>,
    val status: String
)

package hous.release.domain.entity

interface ToDo {
    val isChecked: Boolean
    val todoId: Int
    val todoName: String
    val nicknames: List<String>
    val status: String
}

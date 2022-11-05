package hous.release.domain.entity.response

data class ToDoContent(
    val isPushNotification: Boolean,
    val name: String,
    val todoUsers: List<ToDoUser>
)

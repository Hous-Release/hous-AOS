package hous.release.data.entity.response

data class EditToDoContentResponse(
    val isPushNotification: Boolean,
    val name: String,
    val todoUsers: List<TodoUserResponse>
)

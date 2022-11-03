package hous.release.data.entity.request

import hous.release.domain.entity.UpdateToDoUser

data class UpdateToDoUsersRequest(
    val isPushNotification: Boolean,
    val name: String,
    val todoUsers: List<UpdateToDoUser>
)

package hous.release.data.entity.response

data class HousResponse(
    val homies: List<HomyEntity>,
    val myTodos: List<String>,
    val myTodosCnt: Int,
    val ourRules: List<String>,
    val progress: Int,
    val roomName: String,
    val userNickname: String
)

data class HomyEntity(
    val color: String,
    val homieId: Int,
    val userNickname: String
)

package hous.release.domain.entity.response

data class DomainHousResponse(
    val homies: List<Homy>,
    val myTodos: List<String>,
    val myTodosCnt: Int,
    val ourRules: List<String>,
    val progress: Int,
    val roomName: String,
    val userNickname: String
)

data class Homy(
    val color: String,
    val homieId: Int,
    val userNickname: String
)

package hous.release.domain.entity.response

data class Hous(
    val homies: List<Homy> = emptyList(),
    val myTodos: List<String> = emptyList(),
    val myTodosCnt: Int = -1,
    val ourRules: List<String> = emptyList(),
    val progress: Int = -1,
    val roomName: String = "",
    val userNickname: String = ""
)

data class Homy(
    val color: String = "",
    val homieId: Int = -1,
    val userNickname: String = ""
)

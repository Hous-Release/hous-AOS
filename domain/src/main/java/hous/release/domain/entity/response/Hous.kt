package hous.release.domain.entity.response

data class Hous(
    val homies: List<Homy> = emptyList(),
    val myTodos: List<String> = emptyList(),
    val myTodosCnt: Int = -1,
    val ourRules: List<String> = emptyList(),
    val progress: Int = -1,
    val roomName: String = "",
    val roomCode: String = "",
    val userNickname: String = "",
    val isPersonalityTest: Boolean = false
)

data class Homy(
    val color: String = "",
    val homieId: Int = -1,
    val userNickname: String = ""
)

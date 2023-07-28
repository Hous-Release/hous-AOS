package hous.release.data.entity.response

import com.google.gson.annotations.SerializedName
import hous.release.domain.entity.response.Homy
import hous.release.domain.entity.response.Hous

data class HousResponse(
    val homies: List<HomyEntity>,
    val myTodos: List<String>,
    val myTodosCnt: Int,
    val ourRules: List<String>,
    val progress: Int,
    val roomName: String,
    val roomCode: String,
    val userNickname: String,
    val isPersonalityTest: Boolean
) {
    fun toHous(): Hous = Hous(
        homies = this.homies.map { homyEntity ->
            Homy(
                color = homyEntity.color,
                homieId = homyEntity.homieId,
                userNickname = homyEntity.userNickname
            )
        },
        myTodos = this.myTodos,
        myTodosCnt = this.myTodosCnt,
        ourRules = this.ourRules,
        progress = this.progress,
        roomName = this.roomName,
        roomCode = this.roomCode,
        userNickname = this.userNickname,
        isPersonalityTest = this.isPersonalityTest
    )
}

data class HomyEntity(
    val color: String,
    @SerializedName("onboardingId")
    val homieId: Int,
    val userNickname: String
)

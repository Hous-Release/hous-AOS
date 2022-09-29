package hous.release.data.repository

import hous.release.data.datasource.HousDataSource
import hous.release.domain.entity.response.DomainHousResponse
import hous.release.domain.entity.response.Homy
import hous.release.domain.repository.HousRepository
import javax.inject.Inject

class HousRepositoryImpl @Inject constructor(
    private val housDataSource: HousDataSource
) : HousRepository {
    override suspend fun getHome(): Result<DomainHousResponse> =
        kotlin.runCatching { housDataSource.getHome() }
            .map { response ->
                DomainHousResponse(
                    homies = response.data.homies.map { homy ->
                        Homy(
                            color = homy.color,
                            homieId = homy.homieId,
                            userNickname = homy.userNickname
                        )
                    },
                    myTodos = response.data.myTodos,
                    myTodosCnt = response.data.myTodosCnt,
                    ourRules = response.data.ourRules,
                    progress = response.data.progress,
                    roomName = response.data.roomName,
                    userNickname = response.data.userNickname
                )
            }
}

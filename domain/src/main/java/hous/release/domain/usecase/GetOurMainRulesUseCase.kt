package hous.release.domain.usecase

import hous.release.domain.util.ApiResult
import hous.release.domain.entity.rule.OurRulesContent
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOurMainRulesUseCase @Inject constructor(
    private val repository: OurRulesRepository
) {
    operator fun invoke(): Flow<ApiResult<OurRulesContent>> = flow {
        repository.fetchOurRulesContent().collect { apiResult ->
            when (apiResult) {
                is ApiResult.Empty -> emit(ApiResult.Success(OurRulesContent()))
                is ApiResult.Error -> emit(apiResult)
                is ApiResult.Success -> {
                    val ourRuleList = apiResult.data
                    if (ourRuleList.size <= 3) {
                        val tmpRuleList = OurRulesContent.defaultRuleList.toMutableList()
                        ourRuleList.forEachIndexed { idx, value ->
                            tmpRuleList[idx] = value
                        }
                        emit(
                            ApiResult.Success(
                                OurRulesContent().copy(
                                    ourRuleList = tmpRuleList,
                                    isEmptyRepresentativeRuleList = false,
                                    isEmptyGeneralRuleList = true
                                )
                            )
                        )
                    } else {
                        emit(
                            ApiResult.Success(
                                OurRulesContent().copy(
                                    ourRuleList = ourRuleList,
                                    isEmptyRepresentativeRuleList = false,
                                    isEmptyGeneralRuleList = false
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

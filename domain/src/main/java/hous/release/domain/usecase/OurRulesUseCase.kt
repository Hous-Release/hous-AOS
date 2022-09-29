package hous.release.domain.usecase

import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.OurRulesContent
import hous.release.domain.entity.OurRulesContent.Companion.defaultRuleList
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OurRulesUseCase @Inject constructor(private val repository: OurRulesRepository) {

    fun fetchOurMainRules(): Flow<ApiResult<OurRulesContent>> = flow {
        repository.fetchOurRulesContent().collect { apiResult ->
            when (apiResult) {
                is ApiResult.Empty -> emit(ApiResult.Success(OurRulesContent()))
                is ApiResult.Error -> emit(apiResult)
                is ApiResult.Success -> {
                    val ourRuleList = apiResult.data
                    if (ourRuleList.size <= 3) {
                        val tmpRuleList = defaultRuleList.toMutableList()
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
    }.flowOn(Dispatchers.IO)
}

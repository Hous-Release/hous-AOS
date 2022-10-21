package hous.release.data.repository

import hous.release.data.datasource.OurRulesDataSource
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OurRulesRepositoryImpl @Inject constructor(
    private val ourRulesDataSource: OurRulesDataSource,
    private val ioDispatcher: CoroutineDispatcher
) :
    OurRulesRepository {
    override fun fetchOurRulesContent(): Flow<ApiResult<List<OurRule>>> = flow {
        val response = ourRulesDataSource.getOurRulesContent()
        if (!response.success) {
            emit(ApiResult.Error(response.message))
        } else if (response.data.isEmpty()) {
            emit(ApiResult.Empty)
        } else {
            val data = response.data.map { it.toOurRule() }
            emit(ApiResult.Success(data))
        }
    }.flowOn(ioDispatcher)

    override fun postAddedRule(addedRuleList: List<String>): Flow<ApiResult<String>> = flow {
        val response = ourRulesDataSource.postAddedRuleContent(addedRuleList)
        if (response.success) {
            emit(ApiResult.Success(response.message))
        } else {
            emit(ApiResult.Error(response.message))
        }
    }.flowOn(ioDispatcher)

    override fun putEditedRuleContent(editedRuleList: List<Int>): Flow<ApiResult<String>> = flow {
        val response = ourRulesDataSource.putEditedRuleContent(editedRuleList)
        if (response.success) {
            emit(ApiResult.Success(response.message))
        } else {
            emit(ApiResult.Error(response.message))
        }
    }.flowOn(ioDispatcher)
}

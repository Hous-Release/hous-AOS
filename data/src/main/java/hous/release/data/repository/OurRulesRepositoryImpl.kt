package hous.release.data.repository

import hous.release.data.datasource.OurRulesDataSource
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class OurRulesRepositoryImpl @Inject constructor(
    private val ourRulesDataSource: OurRulesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OurRulesRepository {
    override fun fetchOurRulesContent(): Flow<ApiResult<List<OurRule>>> = flow {
        val response = ourRulesDataSource.getOurRulesContent()
        if (!response.success) {
            emit(ApiResult.Error(response.message))
        } else if (response.data.rules.isEmpty()) {
            emit(ApiResult.Empty)
        } else {
            val data = response.data.rules.map { it.toOurRule() }
            emit(ApiResult.Success(data))
        }
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)

    override fun postAddedRule(addedRuleList: List<String>): Flow<ApiResult<String>> = flow {
        val response = ourRulesDataSource.postAddedRuleContent(addedRuleList)
        if (response.success) {
            emit(ApiResult.Success(response.message))
        } else {
            emit(ApiResult.Error(response.message))
        }
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)

    override fun putEditedRuleContent(editedRuleList: List<OurRule>): Flow<ApiResult<String>> =
        flow {
            val response = ourRulesDataSource.putEditedRuleContent(editedRuleList)
            if (response.success) {
                emit(ApiResult.Success(response.message))
            } else {
                emit(ApiResult.Error(response.message))
            }
        }.catch { e ->
            if (e is HttpException) {
                emit(ApiResult.Error(e.message))
            }
        }.flowOn(ioDispatcher)

    override fun deleteRuleContent(deleteRules: List<Int>): Flow<ApiResult<String>> = flow {
        val response = ourRulesDataSource.deleteRuleContent(deleteRules)
        if (response.success) {
            emit(ApiResult.Success(response.message))
        } else {
            emit(ApiResult.Error(response.message))
        }
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)
}

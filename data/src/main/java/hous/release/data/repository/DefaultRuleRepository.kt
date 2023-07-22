package hous.release.data.repository

import hous.release.data.datasource.RuleDataSource
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.repository.RuleRepository
import hous.release.domain.util.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class DefaultRuleRepository @Inject constructor(
    private val ruleDataSource: RuleDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : RuleRepository {

    override suspend fun fetchMainRules(): List<MainRule> =
        ruleDataSource.fetchMainRules().rules.map { it.toMainRule() }

    override suspend fun postAddedRule(addedRuleList: List<String>): Int {
        var code: Int = -999
        ruleDataSource.postAddedRuleContent(addedRuleList).onSuccess { code = it.status }
            .onFailure { if (it is HttpException) code = it.code() }
        return code
    }

    override fun putEditedRuleContent(editedRuleList: List<MainRule>): Flow<ApiResult<String>> =
        flow {
            val response = ruleDataSource.putEditedRuleContent(editedRuleList)
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
        val response = ruleDataSource.deleteRuleContent(deleteRules)
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
package hous.release.data.repository

import hous.release.data.datasource.RuleDataSource
import hous.release.data.entity.request.rule.AddRulesRequest
import hous.release.data.entity.request.rule.UpdateRuleRequest
import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.Rule
import hous.release.domain.repository.RuleRepository
import hous.release.domain.util.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class RuleRepositoryImpl @Inject constructor(
    private val ruleDataSource: RuleDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : RuleRepository {

    override suspend fun fetchRules(): List<Rule> =
        ruleDataSource.fetchRules().rules.map { it.toRule() }

    override suspend fun fetchDetailRule(id: Int): DetailRule =
        ruleDataSource.fetchDetailRuleBy(id).toDetailRule()

    override suspend fun canAddRule(): Boolean = ruleDataSource.canAddRule()

    override suspend fun addRule(
        description: String,
        name: String,
        imageFiles: List<File>
    ) {
        ruleDataSource.addRule(
            AddRulesRequest(
                description = description,
                name = name,
                imageFiles = imageFiles
            )
        )
    }

    override suspend fun updateRule(
        id: Int,
        description: String,
        name: String,
        imageFiles: List<File>
    ) {
        ruleDataSource.updateRule(
            UpdateRuleRequest(
                id = id,
                description = description,
                name = name,
                imageFiles = imageFiles
            )
        )
    }

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

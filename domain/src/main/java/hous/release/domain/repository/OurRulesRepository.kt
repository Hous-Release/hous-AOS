package hous.release.domain.repository

import hous.release.domain.util.ApiResult
import hous.release.domain.entity.rule.OurRule
import kotlinx.coroutines.flow.Flow

interface OurRulesRepository {
    fun fetchOurRulesContent(): Flow<ApiResult<List<OurRule>>>
    suspend fun postAddedRule(addedRuleList: List<String>): Int
    fun putEditedRuleContent(editedRuleList: List<OurRule>): Flow<ApiResult<String>>
    fun deleteRuleContent(deleteRules: List<Int>): Flow<ApiResult<String>>
}

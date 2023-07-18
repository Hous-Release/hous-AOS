package hous.release.domain.repository

import hous.release.domain.entity.rule.MainRule
import hous.release.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface RuleRepository {
    suspend fun fetchMainRules(): List<MainRule>
    suspend fun postAddedRule(addedRuleList: List<String>): Int
    fun putEditedRuleContent(editedRuleList: List<MainRule>): Flow<ApiResult<String>>
    fun deleteRuleContent(deleteRules: List<Int>): Flow<ApiResult<String>>
}

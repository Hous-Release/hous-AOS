package hous.release.domain.repository

import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import kotlinx.coroutines.flow.Flow

interface OurRulesRepository {
    fun fetchOurRulesContent(): Flow<ApiResult<List<OurRule>>>
    fun postAddedRule(addedRuleList: List<String>): Flow<ApiResult<String>>
    fun putEditedRuleContent(editedRuleList: List<Int>): Flow<ApiResult<String>>
    fun deleteRuleContent(deleteRules: List<Int>): Flow<ApiResult<String>>
}

package hous.release.domain.repository

import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.Rule
import hous.release.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RuleRepository {
    suspend fun fetchRules(): List<Rule>
    suspend fun fetchDetailRule(id: Int): DetailRule

    suspend fun canAddRule(): Boolean
    suspend fun addRule(
        description: String,
        name: String,
        imageFiles: List<File>
    )

    suspend fun updateRule(
        id: Int,
        description: String,
        name: String,
        imageFiles: List<File>
    )

    fun deleteRuleContent(deleteRules: List<Int>): Flow<ApiResult<String>>
}

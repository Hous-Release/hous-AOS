package hous.release.domain.repository

import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.MainRule
import hous.release.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RuleRepository {
    suspend fun fetchMainRules(): List<MainRule>
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

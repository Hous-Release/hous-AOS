package hous.release.domain.repository

import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.MainRule
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

    suspend fun deleteRule(ruleId: Int)
}

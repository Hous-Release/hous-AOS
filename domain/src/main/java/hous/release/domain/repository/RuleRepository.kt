package hous.release.domain.repository

import hous.release.domain.entity.rule.DetailRule
import hous.release.domain.entity.rule.Rule
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

    suspend fun updateRepresentRules(
        rules: List<Int>
    )

    suspend fun deleteRule(ruleId: Int)
}

package hous.release.data.entity.request.rule

import java.io.File

data class UpdateRuleRequest(
    val id: Int,
    val description: String = "",
    val name: String = "",
    val imageFiles: List<File>
)

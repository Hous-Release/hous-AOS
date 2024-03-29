package hous.release.domain.entity.rule

import hous.release.domain.entity.Photo
import hous.release.domain.entity.BaseRule

data class DetailRule(
    override val id: Int = NO_ID,
    override val name: String = NO_NAME,
    val description: String = NO_DESCRIPTION,
    val images: List<Photo> = emptyList(),
    val updatedAt: String = ""
) : BaseRule(id, name) {
    companion object {
        private const val NO_NAME = "제목 없음"
        private const val NO_DESCRIPTION = "설명 없음"
        private const val NO_ID = -1
    }
}

package hous.release.domain.repository

import hous.release.domain.entity.response.OurRule
import kotlinx.coroutines.flow.Flow

interface OurRulesRepository {
    fun fetchOurRulesContent(): Flow<List<OurRule>>
}

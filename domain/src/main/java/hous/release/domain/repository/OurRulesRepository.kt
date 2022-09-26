package hous.release.domain.repository

import hous.release.domain.entity.OurRulesContent
import kotlinx.coroutines.flow.Flow

interface OurRulesRepository {
    fun fetchOurRulesContent(): Flow<OurRulesContent>
}

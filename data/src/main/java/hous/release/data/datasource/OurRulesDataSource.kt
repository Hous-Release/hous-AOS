package hous.release.data.datasource

import hous.release.data.entity.response.OurRulesResponse
import hous.release.data.service.OurRulesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OurRulesDataSource @Inject constructor(private val ourRulesService: OurRulesService) {

    fun getOurRulesContent(): Flow<List<OurRulesResponse>> =
        flow { emit(ourRulesService.getOurRuleContent().data) }
}

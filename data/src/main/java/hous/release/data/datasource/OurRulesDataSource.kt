package hous.release.data.datasource

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.OurRulesResponse
import hous.release.data.service.OurRulesService
import javax.inject.Inject

class OurRulesDataSource @Inject constructor(private val ourRulesService: OurRulesService) {

    suspend fun getOurRulesContent(): BaseResponse<List<OurRulesResponse>> =
        ourRulesService.getOurRuleContent()

    suspend fun postAddedRuleContent(addedRules: List<String>): NoDataResponse =
        ourRulesService.postAddedRuleContent(AddRulesRequest(addedRules = addedRules))

    suspend fun putEditedRuleContent(editedRules: List<Int>): NoDataResponse =
        ourRulesService.putEditedRuleContent(EditRulesRequest(rulesIdList = editedRules))
}

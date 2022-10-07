package hous.release.domain.usecase

import hous.release.domain.entity.OurRulesContent
import hous.release.domain.entity.OurRulesContent.Companion.defaultRuleList
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OurRulesUseCase @Inject constructor(private val repository: OurRulesRepository) {

    operator fun invoke(): Flow<OurRulesContent> =
        repository.fetchOurRulesContent().map { ourRuleList ->
            if (ourRuleList.isEmpty()) {
                OurRulesContent()
            } else if (ourRuleList.size <= 3) {
                val tmpRuleList = defaultRuleList.toMutableList()
                ourRuleList.forEachIndexed { idx, value ->
                    tmpRuleList[idx] = value
                }
                OurRulesContent().copy(
                    ourRuleList = tmpRuleList,
                    isEmptyRepresentativeRuleList = false,
                    isEmptyGeneralRuleList = true
                )
            } else {
                OurRulesContent().copy(
                    ourRuleList = ourRuleList,
                    isEmptyRepresentativeRuleList = false,
                    isEmptyGeneralRuleList = false
                )
            }
        }.catch { exception ->
            if (exception is IndexOutOfBoundsException) {
                emit(
                    OurRulesContent().copy(
                        errorMsg = exception.message
                    )
                )
            }
        }
}

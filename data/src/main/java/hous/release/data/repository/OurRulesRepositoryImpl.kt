package hous.release.data.repository

import android.util.Log
import hous.release.data.datasource.OurRulesDataSource
import hous.release.data.entity.OurRulesEntity
import hous.release.domain.entity.OurRulesContent
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OurRulesRepositoryImpl @Inject constructor(private val ourRulesDataSource: OurRulesDataSource) :
    OurRulesRepository {
    override fun fetchOurRulesContent(): Flow<OurRulesContent> =
        ourRulesDataSource.getOurRulesContent()
            .catch { exception ->
                // 서버 통신 에러 핸들링
                if (exception is Exception) {
                    Log.e("로그", "${exception.message}")
                }
            }.map { ourRuleList ->
                if (ourRuleList.isEmpty()) {
                    OurRulesEntity().copy(errorState = false)
                } else if (ourRuleList.size <= 3) {
                    val tmpRuleList = ourRuleList.toMutableList()
                    OurRulesEntity.defaultRuleList.forEachIndexed { idx, value ->
                        tmpRuleList[idx] = value
                    }
                    OurRulesEntity().copy(
                        errorState = false,
                        ourRuleList = tmpRuleList,
                        isEmptyRepresentativeRuleList = false,
                        isEmptyGeneralRuleList = true
                    )
                } else {
                    OurRulesEntity().copy(
                        errorState = false,
                        ourRuleList = ourRuleList,
                        isEmptyRepresentativeRuleList = false,
                        isEmptyGeneralRuleList = false
                    )
                }
            }.catch { exception ->
                // upStream 에러 핸들링
                if (exception is Exception) {
                    Log.e("로그", "${exception.message}")
                    emit(OurRulesEntity().copy())
                }
            }
}

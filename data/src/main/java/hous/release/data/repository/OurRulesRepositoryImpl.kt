package hous.release.data.repository

import hous.release.data.datasource.OurRulesDataSource
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class OurRulesRepositoryImpl @Inject constructor(private val ourRulesDataSource: OurRulesDataSource) :
    OurRulesRepository {
    override fun fetchOurRulesContent(): Flow<List<OurRule>> =
        ourRulesDataSource.getOurRulesContent()
            .catch { exception ->
                // 서버 통신 에러 핸들링
                if (exception is Exception) {
                    Timber.e(
                        "OurRulesRepositoryImpl - fetchOurRulesContent()- 서버 통신 에러: ${exception.message}"
                    )
                }
                emit(emptyList())
            }.map { ourRulesResponseList ->
                ourRulesResponseList.map { ourRulesResponse -> ourRulesResponse.toOurRule() }
            }
}

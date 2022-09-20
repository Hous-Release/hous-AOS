package hous.release.data.repository

import android.util.Log
import hous.release.data.datasource.OurRulesDataSource
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class OurRulesRepositoryImpl @Inject constructor(private val ourRulesDataSource: OurRulesDataSource) :
    OurRulesRepository {
    override fun fetchOurRulesContent(): Flow<List<OurRule>> =
        ourRulesDataSource.getOurRulesContent()
            .catch { exception ->
                if (exception is Exception) {
                    Log.e("로그", "${exception.message}")
                    emit(emptyList())
                }
            }
}

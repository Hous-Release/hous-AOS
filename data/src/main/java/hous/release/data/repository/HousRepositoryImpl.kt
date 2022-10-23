package hous.release.data.repository

import hous.release.data.datasource.HousDataSource
import hous.release.domain.entity.response.Hous
import hous.release.domain.repository.HousRepository
import javax.inject.Inject

class HousRepositoryImpl @Inject constructor(
    private val housDataSource: HousDataSource
) : HousRepository {
    override suspend fun getHome(): Result<Hous> =
        kotlin.runCatching { housDataSource.getHome() }
            .map { response -> response.data.toHous() }

    override suspend fun putHousName(name: String): Result<Boolean> =
        kotlin.runCatching { housDataSource.putHousName(name) }
            .map { response -> response.success }
}

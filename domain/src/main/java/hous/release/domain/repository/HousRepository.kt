package hous.release.domain.repository

import hous.release.domain.entity.response.Hous

interface HousRepository {
    suspend fun getHome(): Result<Hous>

    suspend fun putHousName(name: String): Result<Boolean>
}

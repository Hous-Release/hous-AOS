package hous.release.domain.repository

import hous.release.domain.entity.response.DomainHousResponse

interface HousRepository {
    suspend fun getHome(): Result<DomainHousResponse>
}

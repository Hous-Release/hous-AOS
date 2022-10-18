package hous.release.domain.usecase

import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.response.OurRule
import hous.release.domain.repository.OurRulesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOurRulesUseCase @Inject constructor(private val repository: OurRulesRepository) {

    operator fun invoke(): Flow<ApiResult<List<OurRule>>> =
        repository.fetchOurRulesContent()
}

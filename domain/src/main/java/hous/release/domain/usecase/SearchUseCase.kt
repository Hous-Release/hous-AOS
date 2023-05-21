package hous.release.domain.usecase

import hous.release.domain.entity.Rule
import javax.inject.Inject

class SearchUseCase @Inject constructor() {
    operator fun invoke(search: String, rules: List<Rule>): List<Rule> {
        return rules
    }
}
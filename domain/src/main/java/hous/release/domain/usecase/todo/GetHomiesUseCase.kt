package hous.release.domain.usecase.todo

import hous.release.domain.entity.todo.Homy
import javax.inject.Inject

class GetHomiesUseCase @Inject constructor() {
    suspend operator fun invoke(): List<Homy> = emptyList()
}

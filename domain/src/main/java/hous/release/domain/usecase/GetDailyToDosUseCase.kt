package hous.release.domain.usecase

import hous.release.domain.repository.ToDoRepository
import javax.inject.Inject

class GetDailyToDosUseCase @Inject constructor(
    private val toDoRepository: ToDoRepository
) {
    suspend operator fun invoke() = toDoRepository.getDailyToDos()
}

package hous.release.domain.usecase

import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetMemberTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke() = todoRepository.getMemberTodos()
}

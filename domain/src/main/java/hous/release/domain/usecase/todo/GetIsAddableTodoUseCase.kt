package hous.release.domain.usecase.todo

import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetIsAddableTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(): Result<Boolean> = todoRepository.getIsAddableTodo()
}

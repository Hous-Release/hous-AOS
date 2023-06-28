package hous.release.domain.usecase.todo

import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: Int) = todoRepository.getTodoDetail(todoId)
}

package hous.release.domain.usecase

import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetEditTodoContentUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    operator fun invoke(todoId: Int) = todoRepository.getEditTodoContent(todoId = todoId)
}

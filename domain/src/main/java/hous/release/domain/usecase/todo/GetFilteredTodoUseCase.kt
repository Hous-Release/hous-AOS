package hous.release.domain.usecase.todo

import hous.release.domain.entity.todo.FilteredTodo
import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetFilteredTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(
        dayOfWeeks: List<String>?,
        onboardingIds: List<Int>?
    ): Result<FilteredTodo> = todoRepository.getFilteredTodos(dayOfWeeks, onboardingIds)
}

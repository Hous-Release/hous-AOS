package hous.release.domain.usecase.todo

import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class GetToDoUsersUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    operator fun invoke() = todoRepository.getToDoUsers()
}

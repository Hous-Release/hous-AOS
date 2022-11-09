package hous.release.domain.usecase

import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class PostAddToDoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    operator fun invoke(
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ) = todoRepository.postAddToDo(isPushNotification, todoUsers, toDoName)
}

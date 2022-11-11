package hous.release.domain.usecase

import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.repository.TodoRepository
import javax.inject.Inject

class PutEditToDoUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    operator fun invoke(
        todoId: Int,
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ) = todoRepository.putEditToDo(todoId, isPushNotification, todoUsers, toDoName)
}

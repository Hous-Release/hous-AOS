package hous.release.domain.usecase.todo

import hous.release.domain.entity.todo.FilteredTodo
import javax.inject.Inject

class GetFilteredTodoUseCase @Inject constructor() {
    suspend operator fun invoke(): FilteredTodo = FilteredTodo(todos = emptyList(), todosCnt = 0)
}
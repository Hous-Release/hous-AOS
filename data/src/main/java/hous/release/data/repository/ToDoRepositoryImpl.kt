package hous.release.data.repository

import hous.release.data.datasource.ToDoDataSource
import hous.release.domain.entity.response.ToDoMain
import hous.release.domain.repository.ToDoRepository
import timber.log.Timber
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDataSource: ToDoDataSource
) : ToDoRepository {
    override suspend fun getToDoMainContent(): Result<ToDoMain> =
        runCatching { toDoDataSource.getToDoMainContent().data }

    override suspend fun checkToDo(todoId: Int, isChecked: Boolean) {
        runCatching { toDoDataSource.checkToDo(todoId = todoId, isChecked = isChecked) }
            .onSuccess { Timber.d("check todo 통신 성공") }
            .onFailure { Timber.d("check todo 통신 실패 : ${it.message}") }
    }
}

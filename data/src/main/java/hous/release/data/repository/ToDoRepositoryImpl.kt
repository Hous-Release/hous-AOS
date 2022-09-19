package hous.release.data.repository

import hous.release.data.datasource.ToDoDataSource
import hous.release.domain.entity.response.ToDoMain
import hous.release.domain.repository.ToDoRepository
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDataSource: ToDoDataSource
) : ToDoRepository {
    override suspend fun getToDoMainContent(): Result<ToDoMain> =
        runCatching { toDoDataSource.getToDoMainContent().data }
}

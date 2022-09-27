package hous.release.data.repository

import android.util.Log
import hous.release.data.datasource.TodoDataSource
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.repository.TodoRepository
import timber.log.Timber
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource
) : TodoRepository {
    override suspend fun getTodoMainContent(): Result<TodoMain> =
        runCatching {
            val result = todoDataSource.getTodoMainContent().data
            Log.d("sdfkjkh", "success : $result")
            Log.d("sdfkjkh", "success : ${result.toTodoMain()}")
            result.toTodoMain()
        }

    override suspend fun checkTodo(todoId: Int, isChecked: Boolean) {
        runCatching { todoDataSource.checkTodo(todoId = todoId, isChecked = isChecked) }
            .onSuccess { Timber.d("check todo 통신 성공") }
            .onFailure { Timber.d("check todo 통신 실패 : ${it.message}") }
    }

    override suspend fun getDailyTodos(): Result<List<TodoMain>> =
        runCatching { todoDataSource.getDailyTodos().data.map { todoMain -> todoMain.toTodoMain() } }
}

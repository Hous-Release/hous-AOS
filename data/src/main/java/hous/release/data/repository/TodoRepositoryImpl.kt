package hous.release.data.repository

import hous.release.data.datasource.TodoDataSource
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.repository.TodoRepository
import timber.log.Timber
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource
) : TodoRepository {
    override suspend fun getTodoMainContent(): Result<TodoMain> =
        runCatching { todoDataSource.getTodoMainContent().data.toTodoMain() }

    override suspend fun checkTodo(todoId: Int, isChecked: Boolean) {
        runCatching { todoDataSource.checkTodo(todoId = todoId, isChecked = isChecked) }
            .onSuccess { Timber.d("check todo 통신 성공") }
            .onFailure { Timber.d("check todo 통신 실패 : ${it.message}") }
    }

    override suspend fun getDailyTodos(): Result<List<TodoMain>> =
        runCatching { todoDataSource.getDailyTodos().data.map { todoMain -> todoMain.toTodoMain() } }

    override suspend fun getMemberTodos(): Result<List<MemberTodoContent>> =
        runCatching { todoDataSource.getMemberTodos().data.map { memberTodoResponse -> memberTodoResponse.toMemberTodoContent() } }

    override suspend fun getTodoDetail(todoId: Int): Result<TodoDetail> =
        runCatching { todoDataSource.getTodoDetail(todoId).data.toTodoDetail() }

    override suspend fun deleteTodo(todoId: Int) {
        runCatching { todoDataSource.deleteTodo(todoId) }
            .onSuccess { Timber.d("delete todo 통신 성공") }
            .onFailure { Timber.d("delete todo 통신 실패 : ${it.message}") }
    }
}

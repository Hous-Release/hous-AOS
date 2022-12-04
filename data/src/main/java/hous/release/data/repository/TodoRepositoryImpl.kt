package hous.release.data.repository

import hous.release.data.datasource.TodoDataSource
import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.entity.response.ToDoContent
import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.repository.TodoRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber

class TodoRepositoryImpl @Inject constructor(
    private val todoDataSource: TodoDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TodoRepository {
    override suspend fun getTodoMainContent(): Result<TodoMain> =
        runCatching { todoDataSource.getTodoMainContent().data.toTodoMain() }

    override suspend fun checkTodo(todoId: Int, isChecked: Boolean): Result<Unit> =
        runCatching { todoDataSource.checkTodo(todoId = todoId, isChecked = isChecked) }

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

    override fun getToDoUsers(): Flow<ApiResult<List<ToDoUser>>> = flow {
        val response = todoDataSource.getToDoUsers()
        if (!response.success) {
            emit(ApiResult.Error(response.message))
            return@flow
        }
        val data = response.data.users.map {
            it.toTodoUser()
        }
        if (data.isEmpty()) {
            emit(ApiResult.Empty)
            return@flow
        }
        emit(ApiResult.Success(data))
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)

    override fun postAddToDo(
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ): Flow<ApiResult<String>> = flow {
        val response = todoDataSource.postAddTodo(
            isPushNotification = isPushNotification,
            toDoName = toDoName,
            updateTodoUsers = todoUsers.map { toDoUser -> toDoUser.toAddedToDoUser() }
        )
        if (!response.success) {
            emit(ApiResult.Error(response.message))
            return@flow
        }
        emit(ApiResult.Success(response.message))
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)

    override fun getEditTodoContent(todoId: Int): Flow<ApiResult<ToDoContent>> = flow {
        val response = todoDataSource.getEditTodoContent(todoId = todoId)
        if (!response.success) {
            emit(ApiResult.Error(response.message))
            return@flow
        }
        val data = response.data
        if (data.todoUsers.isEmpty()) {
            emit(ApiResult.Empty)
            return@flow
        }
        emit(
            ApiResult.Success(
                ToDoContent(
                    isPushNotification = data.isPushNotification,
                    name = data.name,
                    todoUsers = data.todoUsers.map { it.toTodoUser() }
                )
            )
        )
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)

    override fun putEditToDo(
        todoId: Int,
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ): Flow<ApiResult<String>> = flow {
        val response = todoDataSource.putEditToDo(
            todoId = todoId,
            isPushNotification = isPushNotification,
            toDoName = toDoName,
            updateTodoUsers = todoUsers.map { toDoUser -> toDoUser.toAddedToDoUser() }
        )
        if (!response.success) {
            emit(ApiResult.Error(response.message))
            return@flow
        }
        emit(ApiResult.Success(response.message))
    }.catch { e ->
        if (e is HttpException) {
            emit(ApiResult.Error(e.message))
        }
    }.flowOn(ioDispatcher)
}

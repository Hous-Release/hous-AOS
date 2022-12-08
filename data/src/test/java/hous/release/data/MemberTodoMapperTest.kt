package hous.release.data

import hous.release.data.entity.ToDoEntity
import hous.release.data.entity.response.AllMemberTodoResponse
import hous.release.data.entity.response.MemberTodoResponse
import hous.release.data.entity.response.MemberTodoResponse.MemberTodoEntity
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.MemberTodo
import hous.release.domain.entity.Todo
import hous.release.domain.entity.response.AllMemberTodo
import hous.release.domain.entity.response.MemberTodoContent
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MemberTodoMapperTest {
    private val mockTodoEntity = ToDoEntity(
        isChecked = false,
        todoId = 1,
        todoName = "테스트",
        nicknames = listOf("KWY"),
        status = null
    )
    private val mockTodo = Todo(
        isChecked = false,
        todoId = 1,
        todoName = "테스트",
        nicknames = listOf("KWY"),
        status = ""
    )

    private val mockMemberTodoEntity = MemberTodoEntity(
        dayOfWeek = "월",
        todoCnt = 1,
        dayOfWeekTodos = listOf(mockTodoEntity)
    )
    private val mockMemberTodo = MemberTodo(
        dayOfWeek = "월",
        todoCnt = 1,
        dayOfWeekTodos = listOf(mockTodo)
    )

    private val mockMemberTodoResponse = MemberTodoResponse(
        color = "BLUE",
        dayOfWeekTodos = listOf(mockMemberTodoEntity),
        totalTodoCnt = 1,
        userName = "KWY"
    )

    private val mockMemberTodoContent = MemberTodoContent(
        color = HomyType.BLUE,
        dayOfWeekTodos = listOf(mockMemberTodo),
        totalTodoCnt = 1,
        userName = "KWY"
    )

    private val mockAllMemberTodoResponse = AllMemberTodoResponse(
        todos = listOf(mockMemberTodoResponse),
        totalRoomTodoCnt = 1
    )

    private val mockAllMemberTodo = AllMemberTodo(
        todos = listOf(mockMemberTodoContent),
        totalRoomTodoCnt = 1
    )

    @Test
    fun `AllMemberTodoResponse mapper 테스트`() {
        assertEquals(mockAllMemberTodoResponse.toAllMemberTodo(), mockAllMemberTodo)
    }

    @Test
    fun `MemberTodoResponse mapper 테스트`() {
        assertEquals(mockMemberTodoResponse.toMemberTodoContent(), mockMemberTodoContent)
    }

    @Test
    fun `MemberTodoEntity mapper 테스트`() {
        assertEquals(mockMemberTodoEntity.toMemberTodo(), mockMemberTodo)
    }

    @Test
    fun `TodoEntity mapper 테스트`() {
        assertEquals(mockTodoEntity.toTodo(), mockTodo)
    }
}

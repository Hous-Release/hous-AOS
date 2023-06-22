package hous.release.feature.todo.detail.navigation

interface TodoNavigator {
    fun navigateToAddTodo()
    fun navigateToEditTodo(todoId: Int)
}

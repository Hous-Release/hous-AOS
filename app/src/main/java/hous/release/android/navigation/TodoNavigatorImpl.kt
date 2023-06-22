package hous.release.android.navigation

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import hous.release.android.presentation.todo.add.AddToDoActivity
import hous.release.android.presentation.todo.edit.EditToDoActivity
import hous.release.feature.todo.detail.navigation.TodoNavigator
import javax.inject.Inject

class TodoNavigatorImpl @Inject constructor(
    @ActivityContext private val context: Context
) : TodoNavigator {
    override fun navigateToAddTodo() {
        context.startActivity(Intent(context, AddToDoActivity::class.java))
    }

    override fun navigateToEditTodo(todoId: Int) {
        Intent(context, EditToDoActivity::class.java).apply {
            putExtra("todoId", todoId)
        }.also { intent ->
            context.startActivity(intent)
        }
    }
}
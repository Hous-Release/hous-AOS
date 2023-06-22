package hous.release.android.navigation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import dagger.hilt.android.qualifiers.ActivityContext
import hous.release.android.presentation.todo.add.AddToDoActivity
import hous.release.feature.todo.detail.navigation.TodoNavigator
import javax.inject.Inject

class TodoNavigatorImpl @Inject constructor(
    @ActivityContext private val context: Context
) : TodoNavigator {
    override fun navigateToAddTodo() {
        startActivity(context, Intent(context, AddToDoActivity::class.java), null)
    }

    override fun navigateToEditTodo(todoId: Int) {

    }
}
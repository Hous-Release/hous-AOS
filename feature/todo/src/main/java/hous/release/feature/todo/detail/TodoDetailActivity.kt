package hous.release.feature.todo.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.designsystem.theme.HousTheme
import hous.release.feature.todo.R
import hous.release.feature.todo.detail.navigation.TodoNavigator
import javax.inject.Inject

@AndroidEntryPoint
class TodoDetailActivity : ComponentActivity() {
    private val todoDetailViewModel: TodoDetailViewModel by viewModels()

    @Inject
    lateinit var todoNavigator: TodoNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HousTheme {
                TodoDetailScreen(
                    navigateToAddTodo = { todoNavigator.navigateToAddTodo() },
                    navigateToEditTodo = { todoId -> todoNavigator.navigateToEditTodo(todoId) },
                    todoDetailViewModel = todoDetailViewModel,
                    finish = { finish() },
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun onEvent(
        todoEvent: TodoEvent,
        showLimitDialog: () -> Unit
    ) {
        when (todoEvent) {
            TodoEvent.ADD_TODO -> todoNavigator.navigateToAddTodo()
            TodoEvent.SHOW_LIMIT_DIALOG -> showLimitDialog()
            TodoEvent.SHOW_TOAST -> Toast.makeText(
                this,
                getString(R.string.todo_network_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

package hous.release.feature.todo.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.designsystem.theme.HousTheme
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
                    finish = { finish() }
                )
            }
        }
    }
}

package hous.release.feature.todo.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class TodoDetailActivity : ComponentActivity() {
    private val todoDetailViewModel: TodoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HousTheme {
//                TodoDetailScreen(todoDetailViewModel = todoDetailViewModel)
            }
        }
    }
}
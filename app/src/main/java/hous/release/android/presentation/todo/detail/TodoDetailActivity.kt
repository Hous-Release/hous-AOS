package hous.release.android.presentation.todo.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityTodoDetailBinding
import hous.release.android.util.binding.BindingActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoDetailActivity :
    BindingActivity<ActivityTodoDetailBinding>(R.layout.activity_todo_detail) {
    private val todoDetailViewModel: TodoDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectIsFinish()
    }

    private fun collectIsFinish() {
        todoDetailViewModel.isFinish.flowWithLifecycle(lifecycle)
            .onEach { isFinish -> if (isFinish) finish() }
            .launchIn(lifecycleScope)
    }
}

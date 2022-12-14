package hous.release.android.presentation.todo.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityTodoDetailBinding
import hous.release.android.presentation.todo.main.TodoFragment.Companion.CURRENT_DAY
import hous.release.android.presentation.todo.main.TodoState.IDLE
import hous.release.android.presentation.todo.main.TodoState.PROGRESS
import hous.release.android.util.HousLogEvent.SCREEN_TODO_DETAIL
import hous.release.android.util.HousLogEvent.enterScreenLogEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.LoadingDialogFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoDetailActivity :
    BindingActivity<ActivityTodoDetailBinding>(R.layout.activity_todo_detail) {
    private val todoDetailViewModel: TodoDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterScreenLogEvent(SCREEN_TODO_DETAIL, javaClass.name)
        initCurrentDay()
        collectLoadingEvent()
    }

    private fun initCurrentDay() {
        val currentDay = intent.getStringExtra(CURRENT_DAY) ?: "MONDAY"
        when (currentDay) {
            "MONDAY" -> todoDetailViewModel.setDailyTabIndex(0)
            "TUESDAY" -> todoDetailViewModel.setDailyTabIndex(1)
            "WEDNESDAY" -> todoDetailViewModel.setDailyTabIndex(2)
            "THURSDAY" -> todoDetailViewModel.setDailyTabIndex(3)
            "FRIDAY" -> todoDetailViewModel.setDailyTabIndex(4)
            "SATURDAY" -> todoDetailViewModel.setDailyTabIndex(5)
            "SUNDAY" -> todoDetailViewModel.setDailyTabIndex(6)
        }
    }

    private fun collectLoadingEvent() {
        todoDetailViewModel.isLoading.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    IDLE -> (
                        supportFragmentManager.findFragmentByTag(
                            LoadingDialogFragment.TAG
                        ) as? LoadingDialogFragment
                        )?.dismiss()
                    PROGRESS -> LoadingDialogFragment().show(
                        supportFragmentManager,
                        LoadingDialogFragment.TAG
                    )
                }
            }
            .launchIn(lifecycleScope)
    }
}

package hous.release.android.presentation.todo.daily

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityDailyBinding
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.TodoBottomSheet
import hous.release.android.util.binding.BindingActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyActivity : BindingActivity<ActivityDailyBinding>(R.layout.activity_daily) {
    private val dailyAdapter = DailyAdapter(this::showTodoBottomSheet)
    private val dailyViewModel: DailyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarColor()
        initViewPager()
        initTabLayout()
        initClickListener()
        initFloatingButton()
    }

    private fun initClickListener() {
        binding.ivDailyBackButton.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        binding.vpDailyTodos.apply {
            adapter = dailyAdapter
            isUserInputEnabled = false
        }
        dailyViewModel.dailyToDos
            .flowWithLifecycle(lifecycle)
            .onEach { dailyTodos -> dailyAdapter.submitList(dailyTodos) }
            .launchIn(lifecycleScope)
    }

    private fun initTabLayout() {
        dailyViewModel.dailyTabCurrIndex
            .flowWithLifecycle(lifecycle)
            .onEach { currIndex ->
                binding.cvDailyWeekOfDayTab.setContent {
                    DailyTab(
                        currIndex = currIndex,
                        setCurrIndex = dailyViewModel::setTabCurrIndex
                    )
                }
                binding.vpDailyTodos.currentItem = currIndex
            }
            .launchIn(lifecycleScope)
    }

    private fun showTodoBottomSheet(todoId: Int) {
        TodoBottomSheet()
            .apply {
                val bundle = Bundle()
                bundle.putInt("todoId", todoId)
                arguments = bundle
            }
            .also { todoBottomSheet ->
                todoBottomSheet.show(supportFragmentManager, this.javaClass.name)
            }
    }

    private fun initFloatingButton() {
        binding.cvDailyFloatingButton.setContent {
            HousFloatingButton {
                /* TO DO 추가하기 뷰로 이동하는 함수 */
            }
        }
    }

    private fun initStatusBarColor() {
        window?.statusBarColor = ContextCompat.getColor(this, R.color.hous_g_1)
    }
}

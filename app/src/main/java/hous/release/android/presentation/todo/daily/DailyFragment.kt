package hous.release.android.presentation.todo.daily

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.util.TodoBottomSheet
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    private val dailyAdapter = DailyAdapter(this::showTodoBottomSheet)
    private val dailyViewModel: DailyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        initMemberTodosOnClick()
        initTabLayout()
        collectDailyTodos()
    }

    private fun initMemberTodosOnClick() {
//        binding.llDailyChangeView.setOnClickListener {
//            Intent(this, MemberFragment::class.java).also { intent ->
//                startActivity(intent)
//            }
//        }
    }

    private fun initViewPager() {
        binding.vpDailyTodos.apply {
            adapter = dailyAdapter
            isUserInputEnabled = false
        }
    }

    private fun collectDailyTodos() {
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
                bundle.putInt(TODO_ID, todoId)
                arguments = bundle
            }
            .also { todoBottomSheet ->
                todoBottomSheet.show(childFragmentManager, this.javaClass.name)
            }
    }

    companion object {
        const val TODO_ID = "todo_id"
    }
}

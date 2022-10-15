package hous.release.android.presentation.todo.daily

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.presentation.todo.detail.TodoDetailViewModel
import hous.release.android.util.TodoBottomSheet
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    private val dailyAdapter = DailyAdapter(this::showTodoBottomSheet)
    private val todoDetailViewModel: TodoDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        changeTodoDetail()
        initTabLayout()
        collectDailyTodos()
    }

    private fun changeTodoDetail() {
        binding.llDailyChangeView.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_memberFragment)
        }
    }

    private fun initViewPager() {
        binding.vpDailyTodos.apply {
            adapter = dailyAdapter
            isUserInputEnabled = false
        }
    }

    private fun collectDailyTodos() {
        todoDetailViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState -> dailyAdapter.submitList(uiState.dailyTodos) }
            .launchIn(lifecycleScope)
    }

    private fun initTabLayout() {
        todoDetailViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                binding.cvDailyWeekOfDayTab.setContent {
                    DailyTab(
                        currIndex = uiState.dailyTabIndex,
                        setCurrIndex = todoDetailViewModel::setDailyTabIndex
                    )
                }
                binding.vpDailyTodos.currentItem = uiState.dailyTabIndex
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

package hous.release.android.presentation.todo.detail.daily

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.presentation.todo.detail.TodoBottomSheet
import hous.release.android.presentation.todo.detail.TodoDetailViewModel
import hous.release.android.presentation.todo.detail.TodoLimitDialog
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.DailyTab
import hous.release.android.util.component.HousFloatingButton
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.extension.withArgs
import hous.release.android.util.style.HousTheme
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
        initFinishOnClick()
        initFloatingButton()
    }

    override fun onResume() {
        super.onResume()
        todoDetailViewModel.fetchDailyToDos()
    }

    private fun initFinishOnClick() {
        binding.ivDailyBackButton.setOnClickListener { todoDetailViewModel.setIsFinish() }
    }

    private fun changeTodoDetail() {
        binding.llDailyChangeView.setOnClickListener {
            findNavController().navigate(R.id.action_dailyFragment_to_memberFragment)
            todoDetailViewModel.setDailyTabIndex(0)
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
                    HousTheme {
                        DailyTab(
                            currIndex = uiState.dailyTabIndex,
                            setCurrIndex = todoDetailViewModel::setDailyTabIndex
                        )
                    }
                }
                binding.vpDailyTodos.currentItem = uiState.dailyTabIndex
            }
            .launchIn(lifecycleScope)
    }

    private fun showTodoBottomSheet(todoId: Int) {
        TodoBottomSheet().withArgs {
            putInt(TODO_ID, todoId)
            putParcelable(
                TAG,
                ConfirmClickListener(confirmAction = {
                    val action =
                        DailyFragmentDirections.actionDailyFragmentToEditToDoFragment(todoId)
                    findNavController().navigate(action)
                })
            )
        }.show(childFragmentManager, this.javaClass.simpleName)
    }

    private fun initFloatingButton() {
        binding.cvDailyFloatingButton.setContent {
            HousFloatingButton {
                if (todoDetailViewModel.uiState.value.totalTodoCount == 60) {
                    TodoLimitDialog().show(childFragmentManager, this.javaClass.name)
                    return@HousFloatingButton
                }
                findNavController().navigate(R.id.action_dailyFragment_to_addToDoFragment)
            }
        }
    }

    companion object {
        const val TAG = "navigate_To_EditTodoFragment"
        const val TODO_ID = "todo_id"
    }
}

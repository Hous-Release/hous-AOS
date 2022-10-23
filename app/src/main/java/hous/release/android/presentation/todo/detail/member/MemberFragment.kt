package hous.release.android.presentation.todo.detail.member

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentMemberBinding
import hous.release.android.presentation.todo.detail.TodoBottomSheet
import hous.release.android.presentation.todo.detail.TodoDetailViewModel
import hous.release.android.presentation.todo.detail.TodoLimitDialog
import hous.release.android.presentation.todo.detail.daily.DailyFragment
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.MemberTodoTap
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MemberFragment : BindingFragment<FragmentMemberBinding>(R.layout.fragment_member) {
    private val todoDetailViewModel: TodoDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        initViewPager()
        collectMemberTodos()
        changeTodoDetail()
        initFinishOnClick()
        initFloatingButton()
    }

    private fun initFinishOnClick() {
        binding.ivMemberBackButton.setOnClickListener { todoDetailViewModel.setIsFinish() }
    }

    private fun changeTodoDetail() {
        binding.llMemberChangeView.setOnClickListener {
            findNavController().navigate(R.id.action_memberFragment_to_dailyFragment)
        }
    }

    private fun initViewPager() {
        binding.vpMemberTodos.apply {
            adapter = MemberAdapter(this@MemberFragment::showTodoBottomSheet)
            isUserInputEnabled = false
        }
    }

    private fun collectMemberTodos() {
        todoDetailViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                requireNotNull(binding.vpMemberTodos.adapter as MemberAdapter) { "adapter is null" }
                    .submitList(uiState.memberToDos)
            }
            .launchIn(lifecycleScope)
    }

    private fun initTabLayout() {
        todoDetailViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                binding.cvMemberWeekOfDayTab.setContent {
                    MemberTodoTap(
                        currIndex = uiState.memberTabIndex,
                        members = uiState.memberToDos,
                        setCurrIndex = todoDetailViewModel::setMemberTabIndex
                    )
                }
                binding.vpMemberTodos.currentItem = uiState.memberTabIndex
            }
            .launchIn(lifecycleScope)
    }

    private fun showTodoBottomSheet(todoId: Int) {
        TodoBottomSheet()
            .apply {
                val bundle = Bundle()
                bundle.putInt(DailyFragment.TODO_ID, todoId)
                arguments = bundle
            }
            .also { todoBottomSheet ->
                todoBottomSheet.show(childFragmentManager, this.javaClass.name)
            }
    }

    private fun initFloatingButton() {
        binding.cvMemberFloatingButton.setContent {
            HousFloatingButton {
                if (todoDetailViewModel.uiState.value.totalTodoCount == 60) {
                    TodoLimitDialog().show(childFragmentManager, this.javaClass.name)
                    return@HousFloatingButton
                }
                /* TO DO 추가하기 뷰로 이동하는 함수 */
            }
        }
    }
}

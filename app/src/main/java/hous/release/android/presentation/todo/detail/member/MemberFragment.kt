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
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.HousFloatingButton
import hous.release.android.util.component.MemberTodoTap
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.extension.withArgs
import kotlinx.coroutines.delay
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

    override fun onResume() {
        super.onResume()
        todoDetailViewModel.fetchMemberToDos()
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
                delay(10)
                binding.vpMemberTodos.currentItem = uiState.memberTabIndex
            }
            .launchIn(lifecycleScope)
    }

    private fun showTodoBottomSheet(todoId: Int) {
        TodoBottomSheet().withArgs {
            putInt(DailyFragment.TODO_ID, todoId)
            putParcelable(
                DailyFragment.TAG,
                ConfirmClickListener(confirmAction = {
                    val action =
                        MemberFragmentDirections.actionMemberFragmentToEditToDoFragment(todoId)
                    findNavController().navigate(action)
                })
            )
        }.show(childFragmentManager, this.javaClass.simpleName)
    }

    private fun initFloatingButton() {
        binding.cvMemberFloatingButton.setContent {
            HousFloatingButton {
                if (todoDetailViewModel.uiState.value.totalTodoCount >= 60) {
                    TodoLimitDialog().show(childFragmentManager, this.javaClass.name)
                    return@HousFloatingButton
                }
                findNavController().navigate(R.id.action_memberFragment_to_addToDoFragment)
            }
        }
    }
}

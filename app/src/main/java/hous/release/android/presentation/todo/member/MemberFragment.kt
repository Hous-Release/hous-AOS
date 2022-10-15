package hous.release.android.presentation.todo.member

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentMemberBinding
import hous.release.android.presentation.todo.daily.DailyFragment
import hous.release.android.presentation.todo.detail.TodoDetailViewModel
import hous.release.android.util.TodoBottomSheet
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MemberFragment : BindingFragment<FragmentMemberBinding>(R.layout.fragment_member) {
    private val todoDetailViewModel: TodoDetailViewModel by activityViewModels()
    private val memberAdapter = MemberAdapter(this::showTodoBottomSheet)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        initViewPager()
        collectMemberTodos()
        changeTodoDetail()
    }

    private fun changeTodoDetail() {
        binding.llMemberChangeView.setOnClickListener {
            findNavController().navigate(R.id.action_memberFragment_to_dailyFragment)
        }
    }

    private fun initViewPager() {
        binding.vpMemberTodos.apply {
            adapter = memberAdapter
            isUserInputEnabled = false
        }
    }

    private fun collectMemberTodos() {
        todoDetailViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState -> memberAdapter.submitList(uiState.memberToDos) }
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
}

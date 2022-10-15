package hous.release.android.presentation.todo.member

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentMemberBinding
import hous.release.android.presentation.todo.daily.DailyFragment
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.TodoBottomSheet
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MemberFragment : BindingFragment<FragmentMemberBinding>(R.layout.fragment_member) {
    private val memberViewModel: MemberViewModel by viewModels()
    private val memberAdapter = MemberAdapter(this::showTodoBottomSheet)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTabLayout()
        initViewPager()
        collectMemberTodos()
    }

    private fun initViewPager() {
        binding.vpMemberTodos.apply {
            adapter = memberAdapter
            isUserInputEnabled = false
        }
    }

    private fun collectMemberTodos() {
        memberViewModel.memberTodoUiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState -> memberAdapter.submitList(uiState.memberToDos) }
            .launchIn(lifecycleScope)
    }

    private fun initTabLayout() {
        memberViewModel.memberTodoUiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                binding.cvMemberWeekOfDayTab.setContent {
                    MemberTodoTap(
                        currIndex = uiState.memberTabCurrIndex,
                        members = uiState.memberToDos,
                        setCurrIndex = memberViewModel::setTabCurrIndex
                    )
                }
                binding.vpMemberTodos.currentItem = uiState.memberTabCurrIndex
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

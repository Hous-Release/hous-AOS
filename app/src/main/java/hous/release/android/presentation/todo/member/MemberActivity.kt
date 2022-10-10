package hous.release.android.presentation.todo.member

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityMemberBinding
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.binding.BindingActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MemberActivity : BindingActivity<ActivityMemberBinding>(R.layout.activity_member) {
    private val memberViewModel: MemberViewModel by viewModels()
    private val memberAdapter = MemberAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClickListener()
        initFloatingButton()
        initStatusBarColor()
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

    private fun initClickListener() {
        binding.ivMemberBackButton.setOnClickListener {
            finish()
        }
    }

    private fun initFloatingButton() {
        binding.cvMemberFloatingButton.setContent {
            HousFloatingButton {
                /* TO DO 추가하기 뷰로 이동하는 함수 */
            }
        }
    }

    private fun initStatusBarColor() {
        window?.statusBarColor = ContextCompat.getColor(this, R.color.hous_g_1)
    }
}

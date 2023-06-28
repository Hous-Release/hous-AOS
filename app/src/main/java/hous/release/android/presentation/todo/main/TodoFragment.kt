package hous.release.android.presentation.todo.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentToDoBinding
import hous.release.android.presentation.todo.add.AddToDoActivity
import hous.release.android.presentation.todo.detail.TodoLimitDialog
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.RoundedLinearIndicatorWithHomie
import hous.release.android.util.component.TodoMainEmpty
import hous.release.designsystem.component.HousFloatingButton
import hous.release.designsystem.theme.HousTheme
import hous.release.feature.todo.detail.TodoDetailActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoFragment : BindingFragment<FragmentToDoBinding>(R.layout.fragment_to_do) {
    private val toDoViewModel: TodoViewModel by viewModels()
    private var myToDoAdapter: MyTodoAdapter? = null
    private var ourToDoAdapter: OurTodoAdapter? = null

    override fun onResume() {
        super.onResume()
        toDoViewModel.fetchTodoMainContent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = toDoViewModel
        initAdapter()
        showToolTip()
        collectUiState()
        initClickListener()
        collectTodoEvent()
        initFloatingBtn()
    }

    private fun initFloatingBtn() {
        binding.cvToDoFloatingBtn.setContent {
            HousFloatingButton {
                toDoViewModel.getIsAddableTodo()
            }
        }
    }

    private fun collectTodoEvent() {
        toDoViewModel.todoEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(::onEvent)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onEvent(todoEvent: TodoEvent) {
        when (todoEvent) {
            TodoEvent.ADD_TODO -> startActivity(
                Intent(
                    requireActivity(),
                    AddToDoActivity::class.java
                )
            )
            TodoEvent.SHOW_LIMIT_DIALOG -> TodoLimitDialog().show(
                childFragmentManager,
                TodoLimitDialog.TAG
            )
            TodoEvent.SHOW_TOAST -> ToastMessageUtil.showToast(
                requireActivity(),
                getString(R.string.network_error_toast)
            )
        }
    }

    private fun initClickListener() {
        binding.ivToDoViewAll.setOnClickListener {
            Intent(requireActivity(), TodoDetailActivity::class.java)
                .also { intent ->
                    startActivity(intent)
                }
        }
    }

    private fun collectUiState() {
        toDoViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { toDoUiState ->
                binding.cvToDoProgress.setContent {
                    HousTheme {
                        if (toDoUiState.myTodosCount == 0 && toDoUiState.ourTodosCount == 0) {
                            TodoMainEmpty()
                        } else {
                            RoundedLinearIndicatorWithHomie(currentProgress = toDoUiState.progress)
                        }
                    }
                }
                myToDoAdapter?.submitList(toDoUiState.myTodos)
                ourToDoAdapter?.submitList(toDoUiState.ourTodos)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initAdapter() {
        myToDoAdapter = MyTodoAdapter(toDoViewModel::checkTodo)
        ourToDoAdapter = OurTodoAdapter()
        binding.rvToDoMyRules.apply {
            adapter = myToDoAdapter
            itemAnimator = null
        }
        binding.rvToDoOurRules.apply {
            adapter = ourToDoAdapter
            itemAnimator = null
        }
    }

    private fun showToolTip() {
        val balloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.item_to_do_tool_tip)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setCornerRadius(10f)
            .setBackgroundColorResource(R.color.hous_blue_80)
            .setArrowColorResource(R.color.hous_blue_arrow)
            .setMarginRight(18)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()

        binding.ivToDoToolTip.setOnClickListener {
            balloon.showAlignBottom(binding.ivToDoToolTip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myToDoAdapter = null
        ourToDoAdapter = null
    }

    companion object {
        const val CURRENT_DAY = "currentDay"
    }
}

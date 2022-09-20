package hous.release.android.presentation.todo

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
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ToDoFragment : BindingFragment<FragmentToDoBinding>(R.layout.fragment_to_do) {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private var myToDoAdapter: MyToDoAdapter? = null
    private var ourToDoAdapter: OurToDoAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = toDoViewModel
        initAdapter()
        showToolTip()
        collectUiState()
    }

    private fun collectUiState() {
        toDoViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { toDoUiState ->
                binding.cvToDoProgress.setContent {
                    RoundedLinearIndicatorWithHomie(currentProgress = toDoUiState.progress)
                }
                myToDoAdapter?.submitList(toDoUiState.myTodos)
                ourToDoAdapter?.submitList(toDoUiState.ourTodos)
            }
            .launchIn(lifecycleScope)
    }

    private fun initAdapter() {
        myToDoAdapter = MyToDoAdapter(toDoViewModel::checkTodo)
        ourToDoAdapter = OurToDoAdapter()
        binding.rvToDoMyRules.adapter = myToDoAdapter
        binding.rvToDoOurRules.adapter = ourToDoAdapter
    }

    private fun showToolTip() {
        val balloon = Balloon.Builder(requireContext())
            .setLayout(R.layout.item_to_do_tool_tip)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setCornerRadius(4f)
            .setBackgroundColorResource(R.color.hous_blue)
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
}

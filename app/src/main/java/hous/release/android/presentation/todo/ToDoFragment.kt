package hous.release.android.presentation.todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import hous.release.android.R
import hous.release.android.databinding.FragmentToDoBinding
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ToDoFragment : BindingFragment<FragmentToDoBinding>(R.layout.fragment_to_do) {
    private val toDoViewModel: ToDoViewModel by viewModels()
    private var myToDoAdapter: MyToDoAdapter? = null
    private var ourToDoAdapter: OurToDoAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = toDoViewModel
        initAdapter()

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
        myToDoAdapter = MyToDoAdapter()
        ourToDoAdapter = OurToDoAdapter()
        binding.rvToDoMyRules.adapter = myToDoAdapter
        binding.rvToDoOurRules.adapter = ourToDoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myToDoAdapter = null
        ourToDoAdapter = null
    }
}

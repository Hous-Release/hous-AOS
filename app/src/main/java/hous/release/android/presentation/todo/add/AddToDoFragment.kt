package hous.release.android.presentation.todo.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentAddToDoBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.AddTodoUserScreen

@AndroidEntryPoint
class AddToDoFragment : BindingFragment<FragmentAddToDoBinding>(R.layout.fragment_add_to_do) {
    private val viewModel by viewModels<AddToDoVIewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initComposeView()
    }

    private fun initComposeView() {
        binding.composeViewAddToDo.setContent {
            AddTodoUserScreen(viewModel = viewModel)
        }
    }
}

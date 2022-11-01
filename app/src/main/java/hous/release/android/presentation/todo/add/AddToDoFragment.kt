package hous.release.android.presentation.todo.add

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentAddToDoBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class AddToDoFragment : BindingFragment<FragmentAddToDoBinding>(R.layout.fragment_add_to_do) {
    private val viewModel by viewModels<AddToDoVIewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initStatusBarColor()
        initComposeView()
        collectTodoName()
    }

    private fun initComposeView() {
        binding.composeViewAddToDo.setContent {
            TodoUserScreen(
                viewModel = viewModel,
                finish = { findNavController().popBackStack() },
                name = getString(R.string.to_do_add_button),
                putToDo = viewModel::putTodo
            )
        }
    }

    private fun initStatusBarColor() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.hous_white)
    }

    private fun collectTodoName() {
        repeatOnStarted {
            viewModel.todoName.collect {
                viewModel.setToDoNameState(isBlank = viewModel.isBlankToDoName())
            }
        }
    }
}

package hous.release.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.DialogToDoDeleteBinding
import hous.release.domain.usecase.DeleteTodoUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoDeleteDialog : DialogFragment() {
    @Inject
    lateinit var deleteTodoUseCase: DeleteTodoUseCase
    private var todoId: Int = 0
    private var _binding: DialogToDoDeleteBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todoId = arguments?.getInt("todoId") ?: 0
        _binding = DialogToDoDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initDeleteOnClick()
        initCancelOnClick()
    }

    private fun initLayout() {
        dialog?.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawableResource(R.drawable.inset_shape_white_fill_8_rect)
        }
    }

    private fun initDeleteOnClick() {
        binding.tvDialogDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                deleteTodoUseCase(todoId)
                dialog?.dismiss()
            }
        }
    }

    private fun initCancelOnClick() {
        binding.tvDialogCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

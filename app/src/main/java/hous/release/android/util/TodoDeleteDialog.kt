package hous.release.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.DialogToDoDeleteBinding
import hous.release.android.presentation.todo.daily.DailyFragment.Companion.TODO_ID
import hous.release.android.presentation.todo.daily.DailyViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TodoDeleteDialog : DialogFragment() {
    private var todoId: Int = 0
    private var _binding: DialogToDoDeleteBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    private val dailyViewModel: DailyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        todoId = arguments?.getInt(TODO_ID) ?: 0
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
        } ?: Timber.e(getString(R.string.null_point_exception))
    }

    private fun initDeleteOnClick() {
        binding.tvDialogDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                dailyViewModel.deleteTodo(todoId)
                dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
            }
        }
    }

    private fun initCancelOnClick() {
        binding.tvDialogCancel.setOnClickListener {
            dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

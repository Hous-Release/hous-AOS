package hous.release.android.presentation.todo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogTodoLimitBinding
import hous.release.android.util.extension.initLayout

class TodoLimitDialog : DialogFragment() {
    private var _binding: DialogTodoLimitBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTodoLimitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initCancelOnClick()
    }

    private fun initCancelOnClick() {
        binding.tvTodoLimitConfirm.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

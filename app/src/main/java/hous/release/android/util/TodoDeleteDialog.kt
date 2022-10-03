package hous.release.android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogToDoDeleteBinding

class TodoDeleteDialog : DialogFragment() {
    private var _binding: DialogToDoDeleteBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogToDoDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDeleteOnClick()
        initCancelOnClick()
    }

    private fun initDeleteOnClick() {
        binding.tvDialogDelete.setOnClickListener {
            /* 제거 api 연동 */
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

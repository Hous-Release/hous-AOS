package hous.release.android.presentation.hous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogWarningBinding
import hous.release.android.util.extension.initLayout

class WarningDialogFragment : DialogFragment() {
    private var _binding: DialogWarningBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = DialogWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        with(binding) {
            title = getString(R.string.edit_hous_name_warning_title)
            desc = getString(R.string.edit_hous_name_warning_desc)
            cancel = getString(R.string.edit_hous_name_warning_cancel)
            confirm = getString(R.string.edit_hous_name_warning_confirm)
        }
        initCancelClickListener()
        initConfirmClickListener()
    }

    private fun initCancelClickListener() {
        binding.tvWarningCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initConfirmClickListener() {
        binding.tvWarningConfirm.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
    }
}


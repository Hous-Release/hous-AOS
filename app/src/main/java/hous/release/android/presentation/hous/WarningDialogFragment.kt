package hous.release.android.presentation.hous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.FragmentWarningDialogBinding

class WarningDialogFragment : DialogFragment() {
    private var _binding: FragmentWarningDialogBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWarningDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            title = getString(R.string.edit_hous_name_warning_title)
            desc = getString(R.string.edit_hous_name_warning_desc)
            cancel = getString(R.string.edit_hous_name_warning_cancel)
            confirm = getString(R.string.edit_hous_name_warning_confirm)
        }
    }
}


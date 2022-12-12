package hous.release.android.presentation.force_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogForceUpdateBinding
import hous.release.android.util.extension.initLayout

class ForceUpdateDialogFragment : DialogFragment() {
    private var _binding: DialogForceUpdateBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogForceUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initLayout(0.66)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

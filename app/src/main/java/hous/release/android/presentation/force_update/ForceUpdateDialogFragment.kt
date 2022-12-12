package hous.release.android.presentation.force_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogForceUpdateBinding
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.extension.initLayout
import timber.log.Timber

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
        initLayout(0.77)
        initConfirmClickListener()
        initCloseClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().finish()
    }

    private fun initConfirmClickListener() {
        binding.tvForceUpdateConfirm.setOnClickListener {
            arguments?.getParcelable<ConfirmClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            dismiss()
        }
    }

    private fun initCloseClickListener() {
        binding.btnForceUpdateClose.setOnClickListener { dismiss() }
    }

    companion object {
        const val CONFIRM_ACTION = "confirmAction"
    }
}

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
import hous.release.android.util.extension.parcelable
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().finish()
    }

    private fun initConfirmClickListener() {
        binding.tvForceUpdateConfirm.setOnClickListener {
            arguments?.parcelable<ConfirmClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            dismiss()
        }
    }

    companion object {
        const val CONFIRM_ACTION = "confirmAction"
    }
}

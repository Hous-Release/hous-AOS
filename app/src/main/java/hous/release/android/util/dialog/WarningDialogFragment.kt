package hous.release.android.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogWarningBinding
import hous.release.android.util.extension.initLayout
import hous.release.android.util.extension.parcelable
import hous.release.android.util.extension.serializable
import timber.log.Timber

class WarningDialogFragment : DialogFragment() {
    private var _binding: DialogWarningBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        initLayout()
        initWarningDialogContent()
        initCancelClickListener()
        initConfirmClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initWarningDialogContent() {
        val warningType = arguments?.serializable<WarningType>(WARNING_TYPE)
            ?: error(getString(R.string.null_point_exception_warning_dialog_argument))
        with(binding) {
            warning = when (warningType) {
                WarningType.WARNING_SPLASH -> {
                    tvWarningConfirm.setTextColor(
                        ContextCompat.getColor(requireContext(), R.color.hous_blue)
                    )
                    WarningDialogContent().getWarningSplash(requireContext())
                }

                WarningType.WARNING_EDIT_HOUS_NAME ->
                    WarningDialogContent().getWarningEditHousName(requireContext())

                WarningType.WARNING_ADD_RULE ->
                    WarningDialogContent().getWarningAddRule(requireContext())

                WarningType.WARNING_EDIT_RULE ->
                    WarningDialogContent().getWarningEditRule(requireContext())

                WarningType.WARNING_DELETE_RULE ->
                    WarningDialogContent().getWarningDeleteRule(requireContext())

                WarningType.WARNING_ADD_TO_DO ->
                    WarningDialogContent().getWarningAddToDo(requireContext())

                WarningType.WARNING_EDIT_TO_DO ->
                    WarningDialogContent().getWarningEditToDo(requireContext())

                WarningType.WARNING_DELETE_TO_DO ->
                    WarningDialogContent().getWarningDeleteToDo(requireContext())

                WarningType.WARNING_EDIT_PROFILE ->
                    WarningDialogContent().getWarningEditProfile(requireContext())

                WarningType.WARNING_LOGOUT ->
                    WarningDialogContent().getWarningLogout(requireContext())

                WarningType.WARNING_STOP_TEST ->
                    WarningDialogContent().getWarningStopTest(requireContext())

                WarningType.WARNING_PEED_BACK ->
                    WarningDialogContent().getWarningFeedBack(requireContext())
            }
        }
    }

    private fun initCancelClickListener() {
        binding.tvWarningCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initConfirmClickListener() {
        binding.tvWarningConfirm.setOnClickListener {
            arguments?.parcelable<ConfirmClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            dismiss()
        }
    }

    companion object {
        const val DIALOG_WARNING = "warningDialog"
        const val WARNING_TYPE = "warningType"
        const val CONFIRM_ACTION = "confirmAction"
    }
}

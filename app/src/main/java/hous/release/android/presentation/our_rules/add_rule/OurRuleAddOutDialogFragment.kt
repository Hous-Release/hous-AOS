package hous.release.android.presentation.our_rules.add_rule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.DialogOurRuleAddOutBinding
import hous.release.android.util.extension.initLayout
import timber.log.Timber

class OurRuleAddOutDialogFragment() :
    DialogFragment() {
    private var _binding: DialogOurRuleAddOutBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOurRuleAddOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListener() {
        binding.tvOurRuleAddContinueBtn.setOnClickListener {
            dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
        }
        binding.tvOurRuleAddOutBtn.setOnClickListener {
            dialog?.also { dialog ->
                dialog.dismiss()
                findNavController().popBackStack()
            } ?: Timber.e(getString(R.string.null_point_exception))
        }
    }
}

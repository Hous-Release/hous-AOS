package hous.release.android.presentation.our_rules.add_rule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogOurRuleAddErrorBinding
import hous.release.android.util.extension.initLayout

class OurRuleAddErrorDialogFragment : DialogFragment() {
    private var _binding: DialogOurRuleAddErrorBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOurRuleAddErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout(0.77)
        initClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListener() {
        binding.tvOurRuleAddErrorOkBtn.setOnClickListener {
            dismiss()
        }
    }
}

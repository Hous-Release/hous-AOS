package hous.release.android.presentation.our_rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogOurRuleNavigateBinding
import timber.log.Timber

class OurRulesNavigateBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogOurRuleNavigateBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogOurRuleNavigateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListener() {
        binding.tvOurRuleAdd.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleAddFragment)
            dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
        }
        binding.tvOurRuleEdit.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleEditFragment)
            dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
        }
        binding.tvOurRuleDelete.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleDeleteFragment)
            dialog?.dismiss() ?: Timber.e(getString(R.string.null_point_exception))
        }
    }
}

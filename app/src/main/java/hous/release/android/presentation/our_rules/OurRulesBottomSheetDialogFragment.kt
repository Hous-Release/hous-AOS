package hous.release.android.presentation.our_rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogOurRuleBinding

class OurRulesBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogOurRuleBinding? = null
    private val binding get() = _binding ?: error("binding에 null이 들어갔어요~")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogOurRuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
    }

    private fun initClickListener() {
        binding.tvOurRuleAdd.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleAddFragment)
            dialog?.dismiss()
        }
        binding.tvOurRuleEdit.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleEditFragment)
            dialog?.dismiss()
        }
        binding.tvOurRuleDelete.setOnClickListener {
            findNavController().navigate(R.id.action_ourRulesFragment_to_ourRuleDeleteFragment)
            dialog?.dismiss()
        }
    }

    companion object {
        fun newInstance(): OurRulesBottomSheetDialogFragment = OurRulesBottomSheetDialogFragment()
    }
}

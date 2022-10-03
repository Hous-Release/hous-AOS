package hous.release.android.presentation.our_rules.add_rule

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleAddBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.showToast

class OurRuleAddFragment :
    BindingFragment<FragmentOurRuleAddBinding>(R.layout.fragment_our_rule_add) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivAddRulePlusBtn.setOnClickListener {
            context?.showToast("버튼 누름")
        }
        binding.ivAddRuleBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

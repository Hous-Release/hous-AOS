package hous.release.android.presentation.our_rules

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesAdapter
import hous.release.android.util.ItemDecorationUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.safeLet
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class OurRulesFragment : BindingFragment<FragmentOurRuleBinding>(R.layout.fragment_our_rule) {

    private val viewModel: OurRulesViewModel by viewModels()
    private lateinit var ourRulesAdapter: OurRulesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initClickListener()
        initOurRulesAdapter()
        observeOurRules()
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOurRulesInfo()
        }
    }

    private fun initClickListener() {
        binding.ivMainRuleBackButton.setOnClickListener {
            activity?.finish() ?: Timber.e(getString(R.string.null_point_exception))
        }
        binding.ivMainRuleSettingButton.setOnClickListener {
            val ourRulesBottomSheetDialog = OurRulesNavigateBottomSheet()
            ourRulesBottomSheetDialog.show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initOurRulesAdapter() {
        ourRulesAdapter = OurRulesAdapter()
        safeLet(ourRulesAdapter, context) { ourRulesAdapter, context ->
            binding.rvMainRuleOurRules.run {
                itemAnimator = null
                adapter = ourRulesAdapter
                addItemDecoration(
                    ItemDecorationUtil(context, MARGIN, POSITION)
                )
            }
        } ?: Timber.e("NullPointException - ourRulesAdapter, context : $ourRulesAdapter, $context")
    }

    private fun observeOurRules() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                ourRulesAdapter.submitList(uiState.ourRuleList) {
                    binding.rvMainRuleOurRules.invalidateItemDecorations()
                }
            }
            // TODO 추후에 Loading뷰, Error뷰 처리
        }
    }

    companion object {
        private const val MARGIN = 4
        private const val POSITION = 3
    }
}

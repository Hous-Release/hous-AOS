package hous.release.android.presentation.our_rules

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentOurRuleBinding
import hous.release.android.presentation.our_rules.adapter.OurRulesAdapter
import hous.release.android.util.ItemDecorationUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.safeLet
import timber.log.Timber

@AndroidEntryPoint
class OurRulesFragment : BindingFragment<FragmentOurRuleBinding>(R.layout.fragment_our_rule) {

    private val viewModel: OurRulesViewModel by hiltNavGraphViewModels(R.id.nav_our_rules)
    private var ourRulesAdapter: OurRulesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        setOurRulesAdapter()
        observeOurRules()
    }

    override fun onResume() {
        super.onResume()
        // 여기에서 서버통신을 하는게 맞을 지 고민이 되네요
        // 일단은 프래그먼트 백스택에서 꺼내올 때, 서버통신을 하도록 넣어놓았습니다 ㅎ ㅎ ㅎ
        viewModel.getOurRulesInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ourRulesAdapter = null
    }

    private fun setOurRulesAdapter() {
        ourRulesAdapter = OurRulesAdapter()
        safeLet(ourRulesAdapter, context) { ourRulesAdapter, context ->
            binding.rvOurRules.run {
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
                ourRulesAdapter?.let { ourRulesAdapter ->
                    // this callback runs when the list is updated
                    ourRulesAdapter.submitList(uiState.ourRuleList) {
                        binding.rvOurRules.invalidateItemDecorations()
                    }
                }
            }
        }
    }

    companion object {
        private const val MARGIN = 4
        private const val POSITION = 3
    }
}

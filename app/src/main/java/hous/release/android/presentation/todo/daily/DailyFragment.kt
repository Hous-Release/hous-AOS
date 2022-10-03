package hous.release.android.presentation.todo.daily

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    private val dailyAdapter = DailyAdapter()
    private val dailyViewModel: DailyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusBarColor()
        initViewPager()
        initTabLayout()
        initClickListener()
        initFloatingButton()
    }

    private fun initClickListener() {
        binding.ivDailyBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initViewPager() {
        binding.vpDailyTodos.apply {
            adapter = dailyAdapter
            isUserInputEnabled = false
        }
        dailyViewModel.dailyToDos
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { dailyTodos -> dailyAdapter.submitList(dailyTodos) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initTabLayout() {
        dailyViewModel.dailyTabCurrIndex
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { currIndex ->
                binding.cvDailyWeekOfDayTab.setContent {
                    DailyTab(
                        currIndex = currIndex,
                        setCurrIndex = dailyViewModel::setTabCurrIndex
                    )
                }
                binding.vpDailyTodos.currentItem = currIndex
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initFloatingButton() {
        binding.cvDailyFloatingButton.setContent {
            HousFloatingButton {
                /* TO DO 추가하기 뷰로 이동하는 함수 */
            }
        }
    }

    private fun initStatusBarColor() {
        activity?.window?.statusBarColor = getColor(requireActivity(), R.color.hous_g_1)
    }
}

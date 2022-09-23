package hous.release.android.presentation.todo.daily

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    private val dailyAdapter = DailyAdapter()
    private val dailyViewModel: DailyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        dailyViewModel.dailyToDos
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { dailyTodos -> dailyAdapter.submitList(dailyTodos) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        binding.vpDailyTodos.adapter = dailyAdapter
    }

    private fun initTabLayout() {
        val weekOfDayArray = resources.getStringArray(R.array.to_do_week_of_day)
        TabLayoutMediator(binding.tlDailyWeekOfDay, binding.vpDailyTodos) { tab, position ->
            tab.text = weekOfDayArray[position]
        }.attach()
    }
}

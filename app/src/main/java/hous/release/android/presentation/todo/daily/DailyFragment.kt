package hous.release.android.presentation.todo.daily

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.util.binding.BindingFragment

class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
    }

    private fun initTabLayout() {
        val weekOfDayArray = resources.getStringArray(R.array.to_do_week_of_day)
        TabLayoutMediator(binding.tlDailyWeekOfDay, binding.vpDailyTodos) { tab, position ->
            tab.text = weekOfDayArray[position]
        }.attach()
    }
}

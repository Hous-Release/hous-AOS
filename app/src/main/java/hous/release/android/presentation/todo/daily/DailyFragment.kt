package hous.release.android.presentation.todo.daily

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import hous.release.android.R
import hous.release.android.databinding.FragmentDailyBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.data.entity.ToDoEntity
import hous.release.domain.entity.ToDo

class DailyFragment : BindingFragment<FragmentDailyBinding>(R.layout.fragment_daily) {
    private val dailyAdapter = DailyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        val dummyList = mutableListOf<ToDo>(
            ToDoEntity(
                isChecked = true,
                todoId = 1,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 2,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 3,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 4,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 5,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 6,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            ),
            ToDoEntity(
                isChecked = true,
                todoId = 7,
                todoName = "청소기",
                nicknames = emptyList(),
                status = ""
            )
        )
        binding.vpDailyTodos.adapter = dailyAdapter
        dailyAdapter.submitList(dummyList)
    }

    private fun initTabLayout() {
        val weekOfDayArray = resources.getStringArray(R.array.to_do_week_of_day)
        TabLayoutMediator(binding.tlDailyWeekOfDay, binding.vpDailyTodos) { tab, position ->
            tab.text = weekOfDayArray[position]
        }.attach()
    }
}

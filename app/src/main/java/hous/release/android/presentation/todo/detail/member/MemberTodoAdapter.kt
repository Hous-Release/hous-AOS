package hous.release.android.presentation.todo.detail.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemToDoMemberDayOfWeekBinding
import hous.release.android.presentation.todo.detail.daily.DailyMyTodoAdapter
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.MemberTodo
import hous.release.domain.entity.Todo

class MemberTodoAdapter(
    private val showTodoBottomSheet: (Int) -> Unit
) : ListAdapter<MemberTodo, MemberTodoAdapter.MemberTodoViewHolder>(MemberTodoComparator) {
    private lateinit var inflater: LayoutInflater

    class MemberTodoViewHolder(
        private val binding: ItemToDoMemberDayOfWeekBinding,
        private val showTodoBottomSheet: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(memberTodo: MemberTodo) {
            binding.tvMemberDayOfWeek.text = "${memberTodo.dayOfWeek}요일"
            binding.tvMemberToDoCount.text = memberTodo.todoCnt.toString()
        }

        fun fetchTodos(todos: List<Todo>) {
            requireNotNull(binding.rvMemberToDo.adapter as DailyMyTodoAdapter) { "adapter is null" }
                .submitList(todos)
        }

        fun initAdapter() {
            if (binding.rvMemberToDo.adapter == null) {
                val dailyAdapter = DailyMyTodoAdapter(showTodoBottomSheet)
                binding.rvMemberToDo.adapter = dailyAdapter
            }
        }

        fun initTodoDetailOnClick() {
            with(binding) {
                clMemberDayOfWeekDetail.setOnClickListener { view ->
                    view.isSelected = !view.isSelected
                    if (view.isSelected) {
                        rvMemberToDo.visibility = View.GONE
                        ivToDoDetail.setImageResource(R.drawable.ic_to_do_up)
                    } else {
                        rvMemberToDo.visibility = View.VISIBLE
                        ivToDoDetail.setImageResource(R.drawable.ic_to_do_down)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberTodoViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoMemberDayOfWeekBinding.inflate(inflater, parent, false)
        return MemberTodoViewHolder(binding, showTodoBottomSheet)
    }

    override fun onBindViewHolder(holder: MemberTodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.initAdapter()
        holder.onBind(current)
        holder.fetchTodos(current.dayOfWeekTodos)
        holder.initTodoDetailOnClick()
    }

    companion object {
        private val MemberTodoComparator = ItemDiffCallback<MemberTodo>(
            onItemsTheSame = { old, new -> old.todoCnt == new.todoCnt },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

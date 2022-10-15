package hous.release.android.presentation.todo.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemToDoMemberDayOfWeekBinding
import hous.release.android.presentation.todo.daily.DailyMyTodoAdapter
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
            val dailyAdapter = DailyMyTodoAdapter(showTodoBottomSheet)
            binding.rvMemberToDo.adapter = dailyAdapter
            dailyAdapter.submitList(todos)
        }

        fun initTodoDetailOnClick(memberTodo: MemberTodo) {
            if (memberTodo.todoCnt != 0) {
                binding.ivToDoDetail.setOnClickListener { view ->
                    view.isSelected = !view.isSelected
                    if (view.isSelected) {
                        binding.rvMemberToDo.visibility = View.GONE
                        binding.ivToDoDetail.setImageResource(R.drawable.ic_to_do_up)
                    } else {
                        binding.rvMemberToDo.visibility = View.VISIBLE
                        binding.ivToDoDetail.setImageResource(R.drawable.ic_to_do_down)
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
        holder.onBind(current)
        holder.fetchTodos(current.dayOfWeekTodos)
        holder.initTodoDetailOnClick(current)
    }

    companion object {
        private val MemberTodoComparator = ItemDiffCallback<MemberTodo>(
            onItemsTheSame = { old, new -> old.todoCnt == new.todoCnt },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

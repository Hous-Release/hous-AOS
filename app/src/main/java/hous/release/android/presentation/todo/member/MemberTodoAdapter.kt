package hous.release.android.presentation.todo.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoMemberDayOfWeekBinding
import hous.release.android.presentation.todo.daily.DailyMyTodoAdapter
import hous.release.domain.entity.MemberTodo
import hous.release.domain.entity.Todo

class MemberTodoAdapter :
    ListAdapter<MemberTodo, MemberTodoAdapter.MemberTodoViewHolder>(MemberTodoComparator) {
    private lateinit var inflater: LayoutInflater

    class MemberTodoViewHolder(
        private val binding: ItemToDoMemberDayOfWeekBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(memberTodo: MemberTodo) {
            binding.tvMemberDayOfWeek.text = "${memberTodo.dayOfWeek}요일"
            binding.tvMemberToDoCount.text = memberTodo.todoCnt.toString()
        }

        fun fetchTodos(todos: List<Todo>) {
            val dailyAdapter = DailyMyTodoAdapter({})
            binding.rvMemberToDo.adapter = dailyAdapter
            dailyAdapter.submitList(todos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberTodoViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoMemberDayOfWeekBinding.inflate(inflater, parent, false)
        return MemberTodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberTodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)
        holder.fetchTodos(current.dayOfWeekTodos)
    }

    companion object {
        private val MemberTodoComparator = object : DiffUtil.ItemCallback<MemberTodo>() {
            override fun areItemsTheSame(oldItem: MemberTodo, newItem: MemberTodo): Boolean {
                return oldItem.todoCnt == newItem.todoCnt
            }

            override fun areContentsTheSame(oldItem: MemberTodo, newItem: MemberTodo): Boolean {
                return oldItem == newItem
            }
        }
    }
}

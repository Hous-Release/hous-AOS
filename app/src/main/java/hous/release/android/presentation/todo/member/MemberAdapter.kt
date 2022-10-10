package hous.release.android.presentation.todo.member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoMemberBinding
import hous.release.domain.entity.MemberTodo
import hous.release.domain.entity.response.MemberTodoContent

class MemberAdapter :
    ListAdapter<MemberTodoContent, MemberAdapter.MemberViewHolder>(TodoMemberComparator) {
    private lateinit var inflater: LayoutInflater

    class MemberViewHolder(
        private val binding: ItemToDoMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(memberTodo: MemberTodoContent) {
            binding.tvToDoDailyTotal.text = memberTodo.totalTodoCnt.toString()
        }

        fun fetchMemberTodos(memberTodos: List<MemberTodo>) {
            val memberTodoAdapter = MemberTodoAdapter()
            binding.rvToDoMember.adapter = memberTodoAdapter
            memberTodoAdapter.submitList(memberTodos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoMemberBinding.inflate(inflater, parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)
        holder.fetchMemberTodos(current.dayOfWeekTodos)
    }

    companion object {
        private val TodoMemberComparator = object : DiffUtil.ItemCallback<MemberTodoContent>() {
            override fun areItemsTheSame(
                oldItem: MemberTodoContent,
                newItem: MemberTodoContent
            ): Boolean {
                return oldItem.totalTodoCnt == newItem.totalTodoCnt
            }

            override fun areContentsTheSame(
                oldItem: MemberTodoContent,
                newItem: MemberTodoContent
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

package hous.release.android.presentation.todo.daily

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyOurRuleBinding
import hous.release.domain.entity.ToDo

class DailyOurToDoAdapter :
    ListAdapter<ToDo, DailyOurToDoAdapter.OurToDoViewHolder>(ToDoComparator) {

    class OurToDoViewHolder(
        private val binding: ItemToDoDailyOurRuleBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: ToDo) {
            with(binding) {
                this.todo = todo

                when (todo.nicknames.size) {
                    ONE_PARTICIPANT -> tvToDoDailyOurRuleParticipants.text = todo.nicknames[0]
                    TWO_PARTICIPANT ->
                        tvToDoDailyOurRuleParticipants.text = todo.nicknames.joinToString(", ")
                    else ->
                        tvToDoDailyOurRuleParticipants.text =
                            "${todo.nicknames[0]} 외 ${todo.nicknames.size - 1}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurToDoViewHolder {
        val view =
            ItemToDoDailyOurRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OurToDoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        const val ONE_PARTICIPANT = 1
        const val TWO_PARTICIPANT = 2
        private val ToDoComparator = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.todoId == newItem.todoId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem === newItem
            }
        }
    }
}

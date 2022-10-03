package hous.release.android.presentation.todo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemToDoOurRuleBinding
import hous.release.domain.entity.Todo

class OurTodoAdapter :
    ListAdapter<Todo, OurTodoAdapter.OurToDoViewHolder>(TodoComparator) {

    class OurToDoViewHolder(
        private val binding: ItemToDoOurRuleBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                this.todo = todo

                when (todo.status) {
                    NO_ONE_CHECKED -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_unchecked)
                    FEW_CHECKED -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_half)
                    ALL_CHECKED -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_checked)
                }

                when (todo.nicknames.size) {
                    ONE_PARTICIPANT -> tvToDoOurRuleParticipants.text = todo.nicknames[0]
                    TWO_PARTICIPANT ->
                        tvToDoOurRuleParticipants.text = todo.nicknames.joinToString(", ")
                    else ->
                        tvToDoOurRuleParticipants.text =
                            "${todo.nicknames[0]} 외 ${todo.nicknames.size - 1}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OurToDoViewHolder {
        val view =
            ItemToDoOurRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OurToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OurToDoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        const val NO_ONE_CHECKED = "EMPTY"
        const val FEW_CHECKED = "FULL"
        const val ALL_CHECKED = "FULL_CHECK"
        const val ONE_PARTICIPANT = 1
        const val TWO_PARTICIPANT = 2
        private val TodoComparator = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.todoId == newItem.todoId
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }
}

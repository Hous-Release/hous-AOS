package hous.release.android.presentation.todo.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemToDoOurRuleBinding
import hous.release.domain.entity.ToDo

class OurToDoAdapter :
    ListAdapter<ToDo, OurToDoAdapter.OurToDoViewHolder>(ToDo_COMPARATOR) {

    class OurToDoViewHolder(
        private val binding: ItemToDoOurRuleBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: ToDo) {
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
        private val ToDo_COMPARATOR = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.todoId == newItem.todoId
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem == newItem
            }
        }
    }
}

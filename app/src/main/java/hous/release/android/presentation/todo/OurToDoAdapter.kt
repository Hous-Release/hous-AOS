package hous.release.android.presentation.todo

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
                    "EMPTY" -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_unchecked)
                    "FULL" -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_half)
                    "FULL_CHECK" -> ivToDoOurCheckBox.setImageResource(R.drawable.ic_to_do_our_checked)
                }

                when (todo.nicknames.size) {
                    1 -> tvToDoOurRuleParticipants.text = todo.nicknames[0]
                    2 ->
                        tvToDoOurRuleParticipants.text =
                            "${todo.nicknames[0]},${todo.nicknames[1]}"
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
        private val ToDo_COMPARATOR = object : DiffUtil.ItemCallback<ToDo>() {
            override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
                return oldItem.todoId == newItem.todoId
            }
        }
    }
}

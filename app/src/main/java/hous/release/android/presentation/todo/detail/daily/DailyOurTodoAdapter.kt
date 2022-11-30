package hous.release.android.presentation.todo.detail.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyOurRuleBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.Todo

class DailyOurTodoAdapter(
    private val showTodoBottomSheet: (Int) -> Unit
) : ListAdapter<Todo, DailyOurTodoAdapter.OurToDoViewHolder>(TodoComparator) {

    class OurToDoViewHolder(
        private val binding: ItemToDoDailyOurRuleBinding,
        private val showTodoBottomSheet: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            with(binding) {
                this.todo = todo

                clOurToDo.setOnClickListener {
                    showTodoBottomSheet(todo.todoId)
                }

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
        return OurToDoViewHolder(view, showTodoBottomSheet)
    }

    override fun onBindViewHolder(holder: OurToDoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        const val ONE_PARTICIPANT = 1
        const val TWO_PARTICIPANT = 2
        private val TodoComparator = ItemDiffCallback<Todo>(
            onItemsTheSame = { old, new -> old.todoId == new.todoId },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

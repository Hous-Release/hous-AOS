package hous.release.android.presentation.todo.detail.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyMyRuleBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.Todo

class DailyMyTodoAdapter(
    private val showTodoBottomSheet: (Int) -> Unit
) :
    ListAdapter<Todo, DailyMyTodoAdapter.TodoViewHolder>(TodoMainComparator) {

    class TodoViewHolder(
        private val binding: ItemToDoDailyMyRuleBinding,
        private val showTodoBottomSheet: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            with(binding) {
                this.todo = todo
                clMyToDo.setOnClickListener {
                    showTodoBottomSheet(todo.todoId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            ItemToDoDailyMyRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(view, showTodoBottomSheet)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val TodoMainComparator = ItemDiffCallback<Todo>(
            onItemsTheSame = { old, new -> old.todoId == new.todoId },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

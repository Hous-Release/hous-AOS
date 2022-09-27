package hous.release.android.presentation.todo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoMyRuleBinding
import hous.release.domain.entity.Todo

class MyTodoAdapter(
    private val checkToDo: (Int, Boolean) -> Unit
) : ListAdapter<Todo, MyTodoAdapter.TodoViewHolder>(TodoComparator) {

    class TodoViewHolder(private val binding: ItemToDoMyRuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            todo: Todo,
            checkToDo: (Int, Boolean) -> Unit
        ) {
            with(binding) {
                this.todo = todo

                ivToDoMyCheckBox.isSelected = todo.isChecked
                tvToDoMyRule.isSelected = todo.isChecked

                ivToDoMyCheckBox.setOnClickListener {
                    checkToDo(todo.todoId, !todo.isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = ItemToDoMyRuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, checkToDo)
    }

    companion object {
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

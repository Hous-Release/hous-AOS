package hous.release.android.presentation.todo.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoMyRuleBinding
import hous.release.domain.entity.ToDo

class MyToDoAdapter(
    private val checkToDo: (Int, Boolean) -> Unit
) : ListAdapter<ToDo, MyToDoAdapter.TodoViewHolder>(ToDo_COMPARATOR) {

    class TodoViewHolder(private val binding: ItemToDoMyRuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            todo: ToDo,
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
        private val ToDo_COMPARATOR = object : DiffUtil.ItemCallback<ToDo>() {
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

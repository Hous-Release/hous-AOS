package hous.release.android.presentation.todo.daily

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyBinding
import hous.release.domain.entity.ToDo

class DailyAdapter : ListAdapter<ToDo, DailyAdapter.DailyViewHolder>(ToDo_COMPARATOR) {

    class DailyViewHolder(
        private val binding: ItemToDoDailyBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: ToDo) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = ItemToDoDailyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
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

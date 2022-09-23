package hous.release.android.presentation.todo.daily

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyBinding
import hous.release.domain.entity.ToDo
import hous.release.domain.entity.response.ToDoMain

class DailyAdapter : ListAdapter<ToDoMain, DailyAdapter.DailyViewHolder>(ToDoMain_COMPARATOR) {
    private lateinit var inflater: LayoutInflater

    class DailyViewHolder(
        private val binding: ItemToDoDailyBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: ToDoMain) {
            binding.tvToDoDailyTotal.text = "총 ${todo.ourTodosCnt}개"
        }

        fun fetchDailyMyToDos(myToDos: List<ToDo>) {
            val dailyMyToDoAdapter = DailyMyToDoAdapter()
            binding.rvToDoDailyMyRules.adapter = dailyMyToDoAdapter
            dailyMyToDoAdapter.submitList(myToDos)
        }

        fun fetchDailyOurToDos(ourToDos: List<ToDo>) {
            val dailyOurToDoAdapter = DailyOurToDoAdapter()
            binding.rvToDoDailyOurRules.adapter = dailyOurToDoAdapter
            dailyOurToDoAdapter.submitList(ourToDos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoDailyBinding.inflate(inflater, parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.fetchDailyMyToDos(current.myTodos)
        holder.fetchDailyOurToDos(current.ourTodos)
    }

    companion object {
        private val ToDoMain_COMPARATOR = object : DiffUtil.ItemCallback<ToDoMain>() {
            override fun areItemsTheSame(oldItem: ToDoMain, newItem: ToDoMain): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ToDoMain, newItem: ToDoMain): Boolean {
                return oldItem == newItem
            }
        }
    }
}

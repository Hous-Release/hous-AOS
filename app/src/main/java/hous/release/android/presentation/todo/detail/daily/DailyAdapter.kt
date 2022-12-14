package hous.release.android.presentation.todo.detail.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemToDoDailyBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.Todo
import hous.release.domain.entity.response.TodoMain

class DailyAdapter(
    private val showTodoBottomSheet: (Int) -> Unit
) : ListAdapter<TodoMain, DailyAdapter.DailyViewHolder>(TodoMainComparator) {
    private lateinit var inflater: LayoutInflater

    class DailyViewHolder(
        private val binding: ItemToDoDailyBinding,
        private val showTodoBottomSheet: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoMain) {
            binding.todoMain = todo
        }

        fun fetchDailyMyToDos(myToDos: List<Todo>) {
            val dailyMyToDoAdapter = DailyMyTodoAdapter(showTodoBottomSheet)
            binding.rvToDoDailyMyRules.adapter = dailyMyToDoAdapter
            dailyMyToDoAdapter.submitList(myToDos)
        }

        fun fetchDailyOurToDos(ourToDos: List<Todo>) {
            val dailyOurToDoAdapter = DailyOurTodoAdapter(showTodoBottomSheet)
            binding.rvToDoDailyOurRules.adapter = dailyOurToDoAdapter
            dailyOurToDoAdapter.submitList(ourToDos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoDailyBinding.inflate(inflater, parent, false)
        return DailyViewHolder(binding, showTodoBottomSheet)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.fetchDailyMyToDos(current.myTodos)
        holder.fetchDailyOurToDos(current.ourTodos)
    }

    companion object {
        private val TodoMainComparator = ItemDiffCallback<TodoMain>(
            onItemsTheSame = { old, new -> old.myTodosCnt == new.myTodosCnt },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

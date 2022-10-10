package hous.release.android.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemToDoParticipantBinding
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TodoDetail

class TodoParticipantAdapter :
    ListAdapter<TodoDetail.User, TodoParticipantAdapter.TodoUserViewHolder>(TodoUserComparator) {
    private lateinit var inflater: LayoutInflater

    class TodoUserViewHolder(
        private val binding: ItemToDoParticipantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: TodoDetail.User) {
            val homyColor = when (user.color) {
                HomyType.YELLOW -> R.drawable.shape_yellow_fill_12_circle
                HomyType.RED -> R.drawable.shape_red_fill_12_circle
                HomyType.BLUE -> R.drawable.shape_blue_fill_12_circle
                HomyType.PURPLE -> R.drawable.shape_purple_fill_12_circle
                HomyType.GREEN -> R.drawable.shape_green_fill_12_circle
                HomyType.GRAY -> R.drawable.shape_g_5_fill_12_circle
            }
            binding.ivToDoPersonalColor.setImageResource(homyColor)
            binding.tvToDoParticipant.text = user.nickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoUserViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoParticipantBinding.inflate(inflater, parent, false)
        return TodoUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoUserViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)
    }

    companion object {
        private val TodoUserComparator = object : DiffUtil.ItemCallback<TodoDetail.User>() {
            override fun areItemsTheSame(
                oldItem: TodoDetail.User,
                newItem: TodoDetail.User
            ): Boolean {
                return oldItem.onboardingId == newItem.onboardingId
            }

            override fun areContentsTheSame(
                oldItem: TodoDetail.User,
                newItem: TodoDetail.User
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

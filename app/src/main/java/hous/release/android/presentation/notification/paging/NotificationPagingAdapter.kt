package hous.release.android.presentation.notification.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemNotificationBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.response.NotificationContent

class NotificationPagingAdapter :
    PagingDataAdapter<NotificationContent, NotificationPagingAdapter.NotificationViewHolder>(
        notificationDiffUtil
    ) {
    class NotificationViewHolder(
        private val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationContent) {
            binding.notification = notification
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder =
        NotificationViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        private val notificationDiffUtil =
            ItemDiffCallback<NotificationContent>(
                onItemsTheSame = { old, new -> old.notificationId == new.notificationId },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}

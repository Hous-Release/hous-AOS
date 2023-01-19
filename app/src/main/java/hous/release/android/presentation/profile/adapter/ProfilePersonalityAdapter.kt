package hous.release.android.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemProfilePersonalityBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.PersonalityInfo

class ProfilePersonalityAdapter :
    ListAdapter<PersonalityInfo, ProfilePersonalityAdapter.PersonalityViewHolder>(
        PERSONALITY_DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityViewHolder {
        val binding = ItemProfilePersonalityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonalityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonalityViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PersonalityViewHolder(
        val binding: ItemProfilePersonalityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PersonalityInfo) {
            binding.data = data
        }
    }

    companion object {
        private val PERSONALITY_DIFF_CALLBACK =
            ItemDiffCallback<PersonalityInfo>(
                onItemsTheSame = { old, new -> old.type == new.type },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}

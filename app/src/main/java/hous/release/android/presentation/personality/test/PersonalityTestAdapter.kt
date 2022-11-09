package hous.release.android.presentation.personality.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemPersonalityTestBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.PersonalityTest

class PersonalityTestAdapter :
    ListAdapter<PersonalityTest, PersonalityTestAdapter.PersonalityTestViewHolder>(
        personalityTestComparator
    ) {
    private lateinit var inflater: LayoutInflater

    class PersonalityTestViewHolder(
        private val binding: ItemPersonalityTestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(personalityTest: PersonalityTest) {
            binding.data = personalityTest
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityTestViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonalityTestBinding.inflate(inflater, parent, false)
        return PersonalityTestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonalityTestViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)
    }

    companion object {
        private val personalityTestComparator = ItemDiffCallback<PersonalityTest>(
            onItemsTheSame = { old, new -> old.index == new.index },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}

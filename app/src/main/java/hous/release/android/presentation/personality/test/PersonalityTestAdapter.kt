package hous.release.android.presentation.personality.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemPersonalityTestBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.TestState

class PersonalityTestAdapter(
    private val setTestState: (PersonalityTest) -> Unit,
    private val movePage: (Int) -> Unit
) :
    ListAdapter<PersonalityTest, PersonalityTestAdapter.PersonalityTestViewHolder>(
        personalityTestComparator
    ) {
    private lateinit var inflater: LayoutInflater

    class PersonalityTestViewHolder(
        private val binding: ItemPersonalityTestBinding,
        private val setTestState: (PersonalityTest) -> Unit,
        private val movePage: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(personalityTest: PersonalityTest) {
            with(binding) {
                data = personalityTest
                tvAnswer1.isSelected = personalityTest.testState == TestState.ONE
                tvAnswer2.isSelected = personalityTest.testState == TestState.TWO
                tvAnswer3.isSelected = personalityTest.testState == TestState.THREE
            }
        }

        fun initOnClickListener(personalityTest: PersonalityTest) {
            with(binding) {
                tvAnswer1.setOnClickListener {
                    setTestState(personalityTest.copy(testState = TestState.ONE))
                }
                tvAnswer2.setOnClickListener {
                    setTestState(personalityTest.copy(testState = TestState.TWO))
                }
                tvAnswer3.setOnClickListener {
                    setTestState(personalityTest.copy(testState = TestState.THREE))
                }
                btnTestLeft.setOnClickListener {
                    movePage(PREV)
                }
                btnTestRight.setOnClickListener {
                    movePage(NEXT)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalityTestViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonalityTestBinding.inflate(inflater, parent, false)
        return PersonalityTestViewHolder(binding, setTestState, movePage)
    }

    override fun onBindViewHolder(holder: PersonalityTestViewHolder, position: Int) {
        val current = getItem(position)
        holder.onBind(current)
        holder.initOnClickListener(current)
    }

    companion object {
        private val personalityTestComparator = ItemDiffCallback<PersonalityTest>(
            onItemsTheSame = { old, new -> old.index == new.index },
            onContentsTheSame = { old, new -> old == new }
        )
        private const val PREV = -1
        const val NEXT = 1
    }
}

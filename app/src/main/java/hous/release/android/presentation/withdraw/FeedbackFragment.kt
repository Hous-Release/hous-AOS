package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackBinding
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class FeedbackFragment : BindingFragment<FragmentFeedbackBinding>(R.layout.fragment_feedback) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_feedbackFragment_to_withdrawDoneFragment)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
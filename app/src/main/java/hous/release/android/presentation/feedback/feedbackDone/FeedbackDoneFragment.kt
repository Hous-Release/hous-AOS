package hous.release.android.presentation.feedback.feedbackDone

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackDoneBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class FeedbackDoneFragment :
    BindingFragment<FragmentFeedbackDoneBinding>(R.layout.fragment_feedback_done) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReturnBtnClickListener()
        initBackPressedCallback()
    }

    private fun initReturnBtnClickListener() {
        binding.tvFeedbackDoneReturn.setOnSingleClickListener {
            requireActivity().finish()
        }
    }

    private fun initBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }
    }
}

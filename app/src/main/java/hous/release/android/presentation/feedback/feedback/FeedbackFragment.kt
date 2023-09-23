package hous.release.android.presentation.feedback.feedback

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class FeedbackFragment : BindingFragment<FragmentFeedbackBinding>(R.layout.fragment_feedback) {
    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = feedbackViewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clFeedback.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnFeedbackBack.setOnClickListener {
            requireActivity().finish()
        }
    }
}

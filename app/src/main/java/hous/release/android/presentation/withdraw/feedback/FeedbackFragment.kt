package hous.release.android.presentation.withdraw.feedback

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackBinding
import hous.release.android.presentation.settings.SettingsActivity
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class FeedbackFragment : BindingFragment<FragmentFeedbackBinding>(R.layout.fragment_feedback) {
    private val feedbackViewModel by viewModels<FeedbackViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = feedbackViewModel
        initIsDeleting()
        collectUiEvent()
        collectIsSkip()
        initEditTextClearFocus()
        initBackBtnClickListener()
    }

    private fun initIsDeleting() {
        feedbackViewModel.initIsDeleting(
            requireActivity().intent.getBooleanExtra(SettingsActivity.IS_DELETING, false)
        )
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

    private fun collectIsSkip() {
        repeatOnStarted {
            feedbackViewModel.isSkip.collect { skip ->
                if (skip) {
                    findNavController().navigate(R.id.action_feedbackFragment_to_withdrawFragment)
                }
            }
        }
    }

    private fun collectUiEvent() {
        repeatOnStarted {
            val loadingDialogFragment =
                childFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment

            feedbackViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialogFragment?.show(childFragmentManager, LoadingDialogFragment.TAG)
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialogFragment?.dismiss()
                        if (feedbackViewModel.isDeleting.value) {
                            findNavController().navigate(R.id.action_feedbackFragment_to_withdrawFragment)
                        } else {
                            findNavController().navigate(R.id.action_feedbackFragment_to_withdrawDoneFragment)
                        }
                    }
                    UiEvent.ERROR -> {
                        loadingDialogFragment?.dismiss()
                    }
                }
            }
        }
    }
}

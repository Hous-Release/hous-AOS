package hous.release.android.presentation.withdraw

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class FeedbackFragment : BindingFragment<FragmentFeedbackBinding>(R.layout.fragment_feedback) {
    private val feedbackViewModel by viewModels<FeedbackViewModel>()
    private val loadingDialog = LoadingDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = feedbackViewModel
        collectUiEvent()
        collectIsSkip()
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
            feedbackViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialog.show(childFragmentManager, LoadingDialogFragment.TAG)
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialog.dismiss()
                        findNavController().navigate(R.id.action_feedbackFragment_to_withdrawFragment)
                    }
                    UiEvent.ERROR -> {
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }
}
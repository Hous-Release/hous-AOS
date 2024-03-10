package hous.release.android.presentation.feedback.feedback

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentFeedbackBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.withArgs

@AndroidEntryPoint
class FeedbackFragment : BindingFragment<FragmentFeedbackBinding>(R.layout.fragment_feedback) {
    private val feedbackViewModel by activityViewModels<FeedbackViewModel>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = feedbackViewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
        initBackBtnOnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initEditTextClearFocus() {
        binding.clFeedback.setOnTouchListener { _, _ ->
            KeyBoardUtil.hide(activity = requireActivity())
            return@setOnTouchListener false
        }
    }

    private fun initBackBtnOnClick() {
        requireActivity().onBackPressedDispatcher.apply {
            addCallback {
                showOutDialog()
            }.also { callback ->
                onBackPressedCallback = callback
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnFeedbackBack.setOnClickListener {
            showOutDialog()
        }
    }

    private fun showOutDialog() {
        WarningDialogFragment().withArgs {
            putSerializable(
                WarningDialogFragment.WARNING_TYPE,
                WarningType.WARNING_PEED_BACK
            )
            putParcelable(
                WarningDialogFragment.CONFIRM_ACTION,
                ConfirmClickListener(confirmAction = { requireActivity().finish() })
            )
        }.show(childFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }
}

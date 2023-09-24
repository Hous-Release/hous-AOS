package hous.release.android.presentation.feedback

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityFeedbackBinding
import hous.release.android.presentation.feedback.feedback.FeedbackViewModel
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class FeedbackActivity : BindingActivity<ActivityFeedbackBinding>(R.layout.activity_feedback) {
    private val feedbackViewModel by viewModels<FeedbackViewModel>()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fc_feedback) as NavHostFragment
        navHostFragment.navController
    }

    private val loadingDialogFragment by lazy {
        supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment
            ?: LoadingDialogFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIsDeleting()
        collectFeedbackUiEvent()
    }

    private fun initIsDeleting() {
        feedbackViewModel.initIsDeleting(false)
    }

    private fun collectFeedbackUiEvent() {
        repeatOnStarted {
            feedbackViewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.LOADING -> {
                        loadingDialogFragment?.show(
                            supportFragmentManager,
                            LoadingDialogFragment.TAG
                        )
                    }
                    UiEvent.SUCCESS -> {
                        loadingDialogFragment?.dismiss()
                        navController.navigate(R.id.action_feedbackFragment_to_feedbackDoneFragment)
                    }
                    UiEvent.ERROR -> {
                        loadingDialogFragment?.dismiss()
                    }
                }
            }
        }
    }
}

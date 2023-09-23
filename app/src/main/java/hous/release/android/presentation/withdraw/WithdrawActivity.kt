package hous.release.android.presentation.withdraw

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityWithdrawBinding
import hous.release.android.presentation.feedback.feedback.FeedbackViewModel
import hous.release.android.util.UiEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val feedbackViewModel by viewModels<FeedbackViewModel>()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fc_withdraw) as NavHostFragment
        navHostFragment.navController
    }

    private val loadingDialogFragment by lazy {
        supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as? LoadingDialogFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIsDeleting()
        collectFeedbackUiEvent()
        collectIsSkip()
    }

    private fun initIsDeleting() {
        feedbackViewModel.initIsDeleting(true)
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
                        navController.navigate(R.id.action_feedbackFragment_to_withdrawFragment)
                    }
                    UiEvent.ERROR -> {
                        loadingDialogFragment?.dismiss()
                    }
                }
            }
        }
    }

    private fun collectIsSkip() {
        repeatOnStarted {
            feedbackViewModel.isSkip.collect { skip ->
                if (skip) {
                    navController.navigate(R.id.action_feedbackFragment_to_withdrawFragment)
                }
            }
        }
    }

}

package hous.release.android.presentation.withdraw

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityWithdrawBinding
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.repeatOnStarted
import hous.release.domain.entity.FeedbackType

@AndroidEntryPoint
class WithdrawActivity :
    BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {
    private val viewModel by viewModels<WithdrawViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initIsSuccessWithdrawCollector()
        initWithdrawFeedbackSpinner()
    }

    private fun initIsSuccessWithdrawCollector() {
        repeatOnStarted {
            viewModel.isSuccessWithdraw.collect { isSuccess ->
                if (isSuccess) {
                    startActivity(
                        Intent(this, LoginActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                    )
                }
            }
        }
    }

    private fun initWithdrawFeedbackSpinner() {
        with(binding.spinnerWithdrawFeedbackSelect) {
            adapter = ArrayAdapter<String>(
                this@WithdrawActivity,
                R.layout.item_withdraw_feedback_title,
                feedbackTypeItems,
            ).apply {
                setDropDownViewResource(R.layout.item_withdraw_feedback)
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.initFeedbackType(
                        when (feedbackTypeItems[position]) {
                            FeedbackType.NO.type -> FeedbackType.NO
                            FeedbackType.DONE_LIVING_TOGETHER.type -> FeedbackType.DONE_LIVING_TOGETHER
                            FeedbackType.INCONVENIENT_TO_USE.type -> FeedbackType.INCONVENIENT_TO_USE
                            FeedbackType.LOW_USAGE.type -> FeedbackType.LOW_USAGE
                            FeedbackType.CONTENTS_UNSATISFACTORY.type -> FeedbackType.CONTENTS_UNSATISFACTORY
                            FeedbackType.ETC.type -> FeedbackType.ETC
                            else -> FeedbackType.NO
                        }
                    )
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        }
    }

    companion object {
        private val feedbackTypeItems = listOf<String>(
            FeedbackType.NO.type,
            FeedbackType.DONE_LIVING_TOGETHER.type,
            FeedbackType.INCONVENIENT_TO_USE.type,
            FeedbackType.LOW_USAGE.type,
            FeedbackType.CONTENTS_UNSATISFACTORY.type,
            FeedbackType.ETC.type
        )
    }
}

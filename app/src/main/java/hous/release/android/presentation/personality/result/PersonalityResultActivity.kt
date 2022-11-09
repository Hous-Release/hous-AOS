package hous.release.android.presentation.personality.result

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityPersonalityResultBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.component.PersonalityResultImage
import hous.release.android.util.style.HousTheme

@AndroidEntryPoint
class PersonalityResultActivity :
    BindingActivity<ActivityPersonalityResultBinding>(R.layout.activity_personality_result) {
    private val personalityResultViewModel: PersonalityResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = personalityResultViewModel
        initPersonalityResult()
        initTitlePosition()
        initBackOnClickListener()
        initPersonalityImage()
    }

    private fun initPersonalityResult() {
        val resultColor = intent.getStringExtra(RESULT_COLOR) ?: "BLUE"
        personalityResultViewModel.getPersonalityResult(resultColor)
    }

    private fun initTitlePosition() {
        personalityResultViewModel.fromTestResult.observe(this) {
            when (intent.getStringExtra(LOCATION)) {
                RESULT -> personalityResultViewModel.initFromTestResult(true)
                else -> personalityResultViewModel.initFromTestResult(false)
            }
        }
    }

    private fun initPersonalityImage() {
        personalityResultViewModel.resultData.observe(this) { personalityResult ->
            binding.cvPersonalityResultImage.setContent {
                HousTheme {
                    PersonalityResultImage(
                        homyType = personalityResult.color,
                        imageUrl = personalityResult.imageUrl
                    )
                }
            }
        }
    }

    private fun initBackOnClickListener() {
        binding.tvPersonalityResultComplete.setOnClickListener {
            finish()
        }

        binding.ivPersonalityResultBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val LOCATION = "location"
        const val RESULT = "result"
        const val RESULT_COLOR = "resultColor"
    }
}

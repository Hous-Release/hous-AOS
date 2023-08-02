package hous.release.android.presentation.personality.result

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityPersonalityResultBinding
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.component.PersonalityResultImage
import hous.release.android.util.extension.repeatOnStarted
import hous.release.designsystem.theme.HousTheme

@AndroidEntryPoint
class PersonalityResultActivity :
    BindingActivity<ActivityPersonalityResultBinding>(R.layout.activity_personality_result) {
    private val personalityResultViewModel: PersonalityResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = personalityResultViewModel
        initPersonalityResult()
        initBackBtnClickListener()
        initPersonalityImage()
    }

    private fun initPersonalityResult() {
        personalityResultViewModel.getPersonalityResult(
            intent.getStringExtra(RESULT_COLOR) ?: "BLUE"
        )
    }

    private fun initPersonalityImage() {
        repeatOnStarted {
            personalityResultViewModel.uiState.collect { uiState ->
                binding.cvPersonalityResultImage.setContent {
                    HousTheme {
                        PersonalityResultImage(
                            homyType = uiState.color,
                            imageUrl = uiState.imageUrl
                        )
                    }
                }
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnPersonalityResultBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val RESULT_COLOR = "resultColor"
    }
}

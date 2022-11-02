package hous.release.android.presentation.personality.result

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityPersonalityResultBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class PersonalityResultActivity :
    BindingActivity<ActivityPersonalityResultBinding>(R.layout.activity_personality_result) {
    private val personalityResultViewModel: PersonalityResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = personalityResultViewModel
        initTitlePosition()
        initBackOnClickListener()
    }

    private fun initTitlePosition() {
        personalityResultViewModel.fromTestResult.observe(this) {
            when (intent.getStringExtra(LOCATION)) {
                RESULT -> personalityResultViewModel.initFromTestResult(true)
                else -> personalityResultViewModel.initFromTestResult(false)
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
    }
}

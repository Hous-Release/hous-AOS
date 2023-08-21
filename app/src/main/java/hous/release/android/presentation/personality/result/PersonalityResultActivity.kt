package hous.release.android.presentation.personality.result

import android.content.pm.PackageManager
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    personalityResultViewModel.downloadImage()
                } else {
                    showToast(this, "권한을 설정해주세요.")
                }
                return
            }
        }
    }

    companion object {
        const val RESULT_COLOR = "resultColor"
        private const val PERMISSION_REQUEST_CODE = 1
    }
}

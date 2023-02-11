package hous.release.android.presentation.profile.homie

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityHomieProfileBinding
import hous.release.android.presentation.hous.HousFragment.Companion.HOMIE_ID
import hous.release.android.presentation.hous.HousFragment.Companion.HOMIE_POSITION
import hous.release.android.presentation.personality.result.PersonalityResultActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.RESULT_COLOR
import hous.release.android.presentation.profile.ProfileFragment.Companion.personalityInfo
import hous.release.android.presentation.profile.adapter.ProfilePersonalityAdapter
import hous.release.android.util.HousLogEvent.CLICK_MY_PERSONALITY
import hous.release.android.util.HousLogEvent.clickDateLogEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.component.HousPersonalityPentagon
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.style.HousTheme

@AndroidEntryPoint
class HomieProfileActivity :
    BindingActivity<ActivityHomieProfileBinding>(R.layout.activity_homie_profile) {
    private val homieProfileViewModel: HomieProfileViewModel by viewModels()
    private val profilePersonalityAdapter = ProfilePersonalityAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = homieProfileViewModel
        initHomieProfile()
        initBackBtnOnClickListener()
        initHomiePersonalityOnClickListener()
        initHomiePersonalityAdapter()
        initUiStateCollect()
    }

    private fun initHomieProfile() {
        homieProfileViewModel.getHomieProfile(intent.getIntExtra(HOMIE_ID, DEFAULT))
    }

    private fun initBackBtnOnClickListener() {
        binding.btnHomieProfileBack.setOnClickListener {
            finish()
        }
    }

    private fun initHomiePersonalityOnClickListener() {
        binding.llHomieProfilePersonalityDetail.setOnClickListener {
            if (intent.getIntExtra(HOMIE_POSITION, DEFAULT) == MY) {
                clickDateLogEvent(CLICK_MY_PERSONALITY)
            }
            Intent(this, PersonalityResultActivity::class.java).apply {
                putExtra(LOCATION, HOMIE)
                putExtra(
                    RESULT_COLOR,
                    homieProfileViewModel.uiState.value.personalityColor.toString()
                )
            }.also { toPersonalityDetail ->
                startActivity(toPersonalityDetail)
            }
        }
    }

    private fun initHomiePersonalityAdapter() {
        binding.rvHomiePersonalityInfo.adapter = profilePersonalityAdapter
        profilePersonalityAdapter.submitList(personalityInfo)
    }

    private fun initUiStateCollect() {
        repeatOnStarted {
            homieProfileViewModel.uiState.collect { uiState ->
                with(binding.tvHomieProfileIntroduction) {
                    if (uiState.introduction.isNullOrBlank()) {
                        setText(R.string.homie_profile_introduction_empty)
                        setTextColor(getColor(R.color.hous_g_4))
                    } else {
                        text = uiState.introduction
                        setTextColor(getColor(R.color.hous_g_6))
                    }
                }

                binding.cvHomieProfilePersonalityPentagon.setContent {
                    HousTheme {
                        HousPersonalityPentagon(
                            testScore = uiState.testScore,
                            homyType = uiState.personalityColor
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val HOMIE = "homie"
        private const val DEFAULT = -1
        private const val MY = 0
    }
}

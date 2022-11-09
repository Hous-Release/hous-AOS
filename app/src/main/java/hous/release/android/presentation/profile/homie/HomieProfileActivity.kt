package hous.release.android.presentation.profile.homie

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityHomieProfileBinding
import hous.release.android.presentation.hous.HousFragment.Companion.HOMIE_ID
import hous.release.android.presentation.personality.result.PersonalityResultActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.profile.ProfileFragment.Companion.personalityInfo
import hous.release.android.presentation.profile.adapter.ProfilePersonalityAdapter
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.component.HousPersonalityPentagon
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
        initHomieProfileUi()
        observeHomiePersonalityPentagon()
    }

    private fun initHomieProfile() {
        homieProfileViewModel.initHomieId(intent.getIntExtra(HOMIE_ID, DEFAULT))
        homieProfileViewModel.getHomieProfile()
    }

    private fun initBackBtnOnClickListener() {
        binding.btnHomieProfileBack.setOnClickListener {
            finish()
        }
    }

    private fun initHomiePersonalityOnClickListener() {
        binding.llHomieProfilePersonalityDetail.setOnClickListener {
            val toPersonalityDetail = Intent(this, PersonalityResultActivity::class.java)
            toPersonalityDetail.putExtra(LOCATION, HOMIE)
            startActivity(toPersonalityDetail)
        }
    }

    private fun initHomiePersonalityAdapter() {
        binding.rvHomiePersonalityInfo.adapter = profilePersonalityAdapter
        profilePersonalityAdapter.submitList(personalityInfo)
    }

    private fun initHomieProfileUi() {
        homieProfileViewModel.homieProfileData.observe(this) { homie ->
            binding.tvHomieProfileBirthday.text = homie.birthday?.substring(5..9)
            if (homie.introduction.isNullOrBlank()) {
                with(binding) {
                    tvHomieProfileIntroduction.setText(R.string.homie_profile_introduction_empty)
                    tvHomieProfileIntroduction.setTextColor(
                        getColor(R.color.hous_g_4)
                    )
                }
            } else {
                with(binding) {
                    tvHomieProfileIntroduction.text = homie.introduction
                    tvHomieProfileIntroduction.setTextColor(
                        getColor(R.color.hous_g_6)
                    )
                }
            }
        }
    }

    private fun observeHomiePersonalityPentagon() {
        homieProfileViewModel.homieProfileData.observe(this) { homie ->
            binding.cvHomieProfilePersonalityPentagon.setContent {
                HousTheme {
                    HousPersonalityPentagon(
                        testScore = homie.testScore,
                        homyType = homie.personalityColor
                    )
                }
            }
        }
    }

    companion object {
        private val HOMIE = "homie"
        private val DEFAULT = -1
    }
}

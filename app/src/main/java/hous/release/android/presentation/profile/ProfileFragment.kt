package hous.release.android.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentProfileBinding
import hous.release.android.presentation.badge.BadgeActivity
import hous.release.android.presentation.notification.NotificationActivity
import hous.release.android.presentation.personality.PersonalityActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.RESULT_COLOR
import hous.release.android.presentation.profile.adapter.ProfilePersonalityAdapter
import hous.release.android.presentation.profile.edit.ProfileEditActivity
import hous.release.android.presentation.profile.edit.ProfileEntity
import hous.release.android.presentation.settings.SettingsActivity
import hous.release.android.util.HousLogEvent.CLICK_MY_PERSONALITY
import hous.release.android.util.HousLogEvent.CLICK_RE_TEST
import hous.release.android.util.HousLogEvent.clickDateLogEvent
import hous.release.android.util.HousLogEvent.clickLogEvent
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.HousPersonalityPentagon
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.PersonalityInfo

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val profilePersonalityAdapter = ProfilePersonalityAdapter()

    override fun onResume() {
        super.onResume()
        profileViewModel.getProfile()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel
        collectUiState()
        initNotificationOnClickListener()
        initSettingOnClickListener()
        initBadgeOnClickListener()
        initEditOnClickListener()
        initTestBtnOnClickListener()
        initPersonalityAdapter()
        initPersonalityOnClickListener()
    }

    private fun collectUiState() {
        repeatOnStarted {
            profileViewModel.uiState.collect { uiState ->
                with(binding) {
                    if (uiState.profile.introduction.isNullOrEmpty()) {
                        tvProfileIntroduction.setText(R.string.profile_empty_introduction)
                        tvProfileIntroduction.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.hous_g_4)
                        )
                    } else {
                        tvProfileIntroduction.text = uiState.profile.introduction
                        tvProfileIntroduction.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.hous_g_6
                            )
                        )
                    }
                }

                binding.cvProfilePersonalityPentagon.setContent {
                    HousTheme {
                        HousPersonalityPentagon(
                            testScore = uiState.profile.testScore,
                            homyType = uiState.profile.personalityColor
                        )
                    }
                }
            }
        }
    }

    private fun initNotificationOnClickListener() {
        binding.btnProfileNotification.setOnSingleClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }
    }

    private fun initPersonalityOnClickListener() {
        binding.llProfilePersonalityDetail.setOnSingleClickListener {
            clickDateLogEvent(CLICK_MY_PERSONALITY)
            startActivity(
                Intent(requireActivity(), PersonalityResultActivity::class.java).apply {
                    putExtra(
                        RESULT_COLOR,
                        profileViewModel.uiState.value.profile.personalityColor.name
                    )
                    putExtra(LOCATION, PROFILE)
                }
            )
        }
    }

    private fun initSettingOnClickListener() {
        binding.btnProfileSetting.setOnSingleClickListener {
            startActivity(
                Intent(requireContext(), SettingsActivity::class.java).apply {
                    putExtra(SettingsActivity.HAS_ROOM, true)
                }
            )
        }
    }

    private fun initBadgeOnClickListener() {
        binding.ivProfileBadge.setOnSingleClickListener {
            startActivity(Intent(requireContext(), BadgeActivity::class.java))
        }

        binding.tvProfileBadgeEmpty.setOnSingleClickListener {
            startActivity(Intent(requireContext(), BadgeActivity::class.java))
        }
    }

    private fun initEditOnClickListener() {
        binding.btnProfileEdit.setOnSingleClickListener {
            startActivity(
                Intent(requireContext(), ProfileEditActivity::class.java).apply {
                    with(profileViewModel.uiState.value.profile) {
                        putExtra(
                            PROFILE,
                            ProfileEntity(
                                nickname,
                                birthday,
                                birthdayPublic,
                                mbti,
                                job,
                                introduction
                            )
                        )
                    }
                }
            )
        }
    }

    private fun initPersonalityAdapter() {
        binding.rvPersonalityInfo.adapter = profilePersonalityAdapter
        profilePersonalityAdapter.submitList(personalityInfo)
    }

    private fun initTestBtnOnClickListener() {
        binding.btnProfileTestAgain.setOnSingleClickListener {
            if (profileViewModel.uiState.value.isTest) clickLogEvent(CLICK_RE_TEST)
            startActivity(Intent(requireContext(), PersonalityActivity::class.java))
        }
    }

    companion object {
        val personalityInfo = listOf(
            PersonalityInfo(R.string.personality_light, R.string.personality_light_description),
            PersonalityInfo(R.string.personality_noise, R.string.personality_noise_description),
            PersonalityInfo(R.string.personality_smell, R.string.personality_smell_description),
            PersonalityInfo(
                R.string.personality_introversion,
                R.string.personality_introversion_description
            ),
            PersonalityInfo(R.string.personality_clean, R.string.personality_clean_description)
        )
        const val PROFILE = "profile"
    }
}

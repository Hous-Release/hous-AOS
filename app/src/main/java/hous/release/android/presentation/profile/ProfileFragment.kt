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
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.component.HousPersonalityPentagon
import hous.release.android.util.style.HousTheme
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.PersonalityInfo
import hous.release.domain.entity.ProfileSet

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
        initProfileInfo()
        initNotificationOnClickListener()
        initSettingOnClickListener()
        initBadgeOnClickListener()
        initEditOnClickListener()
        initTestBtnOnClickListener()
        initPersonalityAdapter()
        initPersonalityOnClickListener()
        observePersonalityPentagon()
    }

    private fun initPersonalityOnClickListener() {
        binding.llProfilePersonalityDetail.setOnClickListener {
            val toPersonalityResult =
                Intent(requireActivity(), PersonalityResultActivity::class.java)
            toPersonalityResult.putExtra(
                RESULT_COLOR,
                profileViewModel.profileData.value!!.personalityColor.name
            )
            toPersonalityResult.putExtra(LOCATION, PROFILE)
            startActivity(toPersonalityResult)
        }
    }

    private fun initNotificationOnClickListener() {
        binding.btnProfileNotification.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }
    }

    private fun initSettingOnClickListener() {
        binding.btnProfileSetting.setOnClickListener {
            binding.btnProfileSetting.setOnClickListener {
                startActivity(
                    Intent(requireContext(), SettingsActivity::class.java).apply {
                        putExtra(SettingsActivity.HAS_ROOM, true)
                    }
                )
            }
        }
    }

    private fun initBadgeOnClickListener() {
        binding.ivProfileBadge.setOnClickListener {
            startActivity(Intent(requireContext(), BadgeActivity::class.java))
        }
    }

    private fun initEditOnClickListener() {
        binding.btnProfileEdit.setOnClickListener {
            val toProfileEdit = Intent(requireContext(), ProfileEditActivity::class.java)
            toProfileEdit.putExtra(
                PROFILE,
                ProfileEntity(
                    profileViewModel.profileData.value!!.nickname,
                    profileViewModel.profileData.value!!.birthday,
                    profileViewModel.profileData.value!!.birthdayPublic,
                    profileViewModel.profileData.value!!.mbti,
                    profileViewModel.profileData.value!!.job,
                    profileViewModel.profileData.value!!.introduction
                )
            )
            startActivity(toProfileEdit)
        }
    }

    private fun initTestBtnOnClickListener() {
        binding.btnProfileTestAgain.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalityActivity::class.java))
        }
    }

    private fun initPersonalityAdapter() {
        binding.rvPersonalityInfo.adapter = profilePersonalityAdapter
        profilePersonalityAdapter.submitList(personalityInfo)
    }

    private fun initProfileInfo() {
        profileViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            if (profile.personalityColor == HomyType.GRAY) binding.clProfileHeader.setBackgroundResource(
                R.color.hous_g_1
            )
            else {
                val profileSet = getProfileSet(profile.personalityColor.toString())
                with(binding) {
                    clProfileHeader.setBackgroundResource(profileSet.colorBg)
                    tvProfilePersonality.setText(profileSet.personality)
                    tvProfilePersonality.setTextColor(
                        ContextCompat.getColor(requireContext(), profileSet.colorText)
                    )
                }
            }
            binding.tvProfileBirthday.text = profile.birthday?.substring(5..9)
            if (profile.introduction.isNullOrEmpty()) {
                with(binding) {
                    tvProfileIntroduction.setText(R.string.profile_empty_introduction)
                    tvProfileIntroduction.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.hous_g_4
                        )
                    )
                }
            } else {
                with(binding) {
                    tvProfileIntroduction.text = profile.introduction
                    tvProfileIntroduction.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.hous_g_6
                        )
                    )
                }
            }
        }
    }

    private fun observePersonalityPentagon() {
        profileViewModel.profileData.observe(viewLifecycleOwner) { profile ->
            binding.cvProfilePersonalityPentagon.setContent {
                HousTheme {
                    HousPersonalityPentagon(
                        testScore = profile.testScore,
                        homyType = profile.personalityColor
                    )
                }
            }
        }
    }

    private fun getProfileSet(colorType: String): ProfileSet {
        return when (HomyType.valueOf(colorType)) {
            HomyType.RED -> ProfileSet(
                colorBg = R.color.hous_red_profile,
                colorText = R.color.hous_red,
                personality = R.string.personality_red
            )
            HomyType.YELLOW -> ProfileSet(
                colorBg = R.color.hous_yellow,
                colorText = R.color.hous_yellow,
                personality = R.string.personality_yellow
            )
            HomyType.GREEN -> ProfileSet(
                colorBg = R.color.hous_green,
                colorText = R.color.hous_green,
                personality = R.string.personality_green
            )
            HomyType.BLUE -> ProfileSet(
                colorBg = R.color.hous_blue,
                colorText = R.color.hous_blue,
                personality = R.string.personality_blue
            )
            HomyType.PURPLE -> ProfileSet(
                colorBg = R.color.hous_purple,
                colorText = R.color.hous_purple,
                personality = R.string.personality_purple
            )
            HomyType.GRAY -> ProfileSet(
                colorBg = -1,
                colorText = -1,
                personality = -1
            )
        }
    }

    companion object {
        private val personalityInfo = listOf(
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

package hous.release.android.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentProfileBinding
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.profile.adapter.ProfilePersonalityAdapter
import hous.release.android.util.binding.BindingFragment
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.PersonalityInfo
import hous.release.domain.entity.ProfileSet
import hous.release.domain.entity.response.PersonalityResult

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val profilePersonalityAdapter = ProfilePersonalityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel
        initProfileInfo()
        initAlarmOnClickListener()
        initSettingOnClickListener()
        initBadgeOnClickListener()
        initEditOnClickListener()
        initTestBtnOnClickListener()
        initPersonalityAdapter()
        initPersonalityOnClickListener()
    }

    private fun initPersonalityOnClickListener() {
        val intent = Intent(requireActivity(), PersonalityResult::class.java)
        intent.putExtra(LOCATION, PROFILE)
        startActivity(intent)
    }

    private fun initAlarmOnClickListener() {
        binding.ivProfileAlarm.setOnClickListener {
            // 알람으로 go
        }
    }

    private fun initSettingOnClickListener() {
        binding.ivProfileSetting.setOnClickListener {
            // 세팅으로 go
        }
    }

    private fun initBadgeOnClickListener() {
        binding.ivProfileBadge.setOnClickListener {
            // 배지로 go
        }
    }

    private fun initEditOnClickListener() {
        binding.ivProfileEdit.setOnClickListener {
            // 수정으로 go
        }
    }

    private fun initTestBtnOnClickListener() {
        binding.btnProfileTestAgain.setOnClickListener {
            // 테스트로 go
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
        private const val PROFILE = "profile"
    }
}

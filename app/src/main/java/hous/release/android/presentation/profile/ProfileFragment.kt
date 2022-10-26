package hous.release.android.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentProfileBinding
import hous.release.android.presentation.profile.adapter.ProfilePersonalityAdapter
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.getProfileSet
import hous.release.domain.entity.PersonalityInfo

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val profilePersonalityAdapter = ProfilePersonalityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel
        initAlarmOnClickListener()
        initSettingOnClickListener()
        initBadgeOnClickListener()
        initEditOnClickListener()
        initTestBtnOnClickListener()
        initPersonalityAdapter()
        initPersonalityColorObserve()
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

    private fun initPersonalityColorObserve() {
        profileViewModel.personalityColor.observe(viewLifecycleOwner) { color ->
            if (color == "GRAY") binding.clProfileHeader.setBackgroundResource(R.color.hous_g_1)
            else {
                val profileSet = getProfileSet(profileViewModel.personalityColor.value!!)
                binding.clProfileHeader.setBackgroundResource(profileSet.colorBg)
                binding.tvProfilePersonality.setText(profileSet.personality)
                binding.tvProfilePersonality.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        profileSet.colorText
                    )
                )
            }
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
    }
}

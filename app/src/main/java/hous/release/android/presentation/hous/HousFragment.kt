package hous.release.android.presentation.hous

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentHousBinding
import hous.release.android.presentation.hous.adapter.HomiesAdapter
import hous.release.android.presentation.main.MainActivity
import hous.release.android.presentation.our_rules.OurRulesActivity
import hous.release.android.presentation.personality.PersonalityActivity
import hous.release.android.presentation.profile.homie.HomieProfileActivity
import hous.release.android.util.HousLogEvent.CLICK_BLANK
import hous.release.android.util.HousLogEvent.CLICK_COPY
import hous.release.android.util.HousLogEvent.CLICK_HOME_HOMIES
import hous.release.android.util.HousLogEvent.clickLogEvent
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.Homy

@AndroidEntryPoint
class HousFragment : BindingFragment<FragmentHousBinding>(R.layout.fragment_hous) {
    private val viewModel by viewModels<HousViewModel>()
    private val homiesAdapter = HomiesAdapter(onClickHomie = ::onClickHomie)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initClickListener()
        initHomiesAdapter()
        initHomiesObserver()
        initNavigateToOurRulesBtnClickListener()
        initMoveToToDoClickListener()
        initCheckPersonalityClickListener()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHome()
    }

    private fun onClickHomie(homy: Homy, position: Int) {
        val currentId = homy.homieId
        clickLogEvent(CLICK_HOME_HOMIES)
        when (HomyType.valueOf(homy.color)) {
            HomyType.GRAY -> {
                ToastMessageUtil.showToast(
                    requireContext(),
                    getString(R.string.hous_not_tested_yet)
                )
            }
            else -> {
                val toHomieProfile = Intent(requireActivity(), HomieProfileActivity::class.java)
                toHomieProfile.apply {
                    putExtra(HOMIE_ID, currentId)
                    putExtra(HOMIE_POSITION, position)
                }
                startActivity(toHomieProfile)
            }
        }
    }

    private fun initClickListener() {
        binding.btnHousEdit.setOnSingleClickListener {
            startActivity(
                Intent(requireContext(), EditHousNameActivity::class.java).apply {
                    putExtra(ROOM_NAME, viewModel.hous.value.roomName)
                }
            )
        }

        binding.btnHousCopyCode.setOnClickListener {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipCode =
                ClipData.newPlainText(ROOM_CODE, viewModel.hous.value.roomCode)
            clipboard.setPrimaryClip(clipCode)
            ToastMessageUtil.showToast(
                requireContext(),
                getString(R.string.hous_toast_copy)
            )
            clickLogEvent(CLICK_COPY)
        }

        binding.lottieHous.setOnClickListener {
            clickLogEvent(CLICK_BLANK)
        }
    }

    private fun initNavigateToOurRulesBtnClickListener() {
        binding.btnHousOurRules.setOnSingleClickListener {
            startActivity(Intent(requireContext(), OurRulesActivity::class.java))
        }
        binding.layoutHousOurRules.setOnSingleClickListener {
            startActivity(Intent(requireContext(), OurRulesActivity::class.java))
        }
    }

    private fun initMoveToToDoClickListener() {
        binding.layoutHousMyTodo.setOnSingleClickListener {
            (requireActivity() as MainActivity).moveToToDoFragment()
        }
    }

    private fun initCheckPersonalityClickListener() {
        binding.btnHousCheckPersonality.setOnSingleClickListener {
            startActivity(Intent(requireContext(), PersonalityActivity::class.java))
        }
    }

    private fun initHomiesAdapter() {
        binding.rvHousHomies.adapter = homiesAdapter
    }

    private fun initHomiesObserver() {
        repeatOnStarted {
            viewModel.homies.collect { homies ->
                homiesAdapter.submitList(homies)
            }
        }
    }

    companion object {
        const val ROOM_NAME = "roomName"
        const val ROOM_CODE = "roomCode"
        const val HOMIE_ID = "homie id"
        const val HOMIE_POSITION = "homie_position"
    }
}

package hous.release.android.presentation.enter_room

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomBinding
import hous.release.android.presentation.settings.SettingsActivity
import hous.release.android.presentation.settings.SettingsActivity.Companion.HAS_ROOM
import hous.release.android.util.binding.BindingFragment

class EnterRoomFragment :
    BindingFragment<FragmentEnterRoomBinding>(R.layout.fragment_enter_room) {
    private var createRoomBtnScaleAnimator: Animator? = null
    private var enterCodeBtnScaleAnimator: Animator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettingsBtnClickListener()
        initCreateRoomBtnClickListener()
        initEnterRoomCodeBtnClickListener()
    }

    private fun initCreateRoomBtnScaleAnimator() {
        createRoomBtnScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_enter_room_button
        ).apply {
            setTarget(binding.layoutEnterRoomCreate)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    createRoomBtnScaleAnimator = null
                    findNavController().navigate(R.id.action_enterRoomFragment_to_createRoomFragment)
                }
            })
        }
    }

    private fun initEnterRoomCodeBtnScaleAnimator() {
        enterCodeBtnScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_enter_room_button
        ).apply {
            setTarget(binding.layoutEnterRoomEnterCode)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    enterCodeBtnScaleAnimator = null
                    findNavController().navigate(R.id.action_enterRoomFragment_to_enterRoomCodeFragment)
                }
            })
        }
    }

    private fun initSettingsBtnClickListener() {
        binding.btnEnterRoomSettings.setOnClickListener {
            startActivity(
                Intent(requireContext(), SettingsActivity::class.java).apply {
                    putExtra(HAS_ROOM, false)
                }
            )
        }
    }

    private fun initCreateRoomBtnClickListener() {
        binding.layoutEnterRoomCreate.setOnClickListener {
            initCreateRoomBtnScaleAnimator()
            createRoomBtnScaleAnimator?.start()
        }
    }

    private fun initEnterRoomCodeBtnClickListener() {
        binding.layoutEnterRoomEnterCode.setOnClickListener {
            initEnterRoomCodeBtnScaleAnimator()
            enterCodeBtnScaleAnimator?.start()
        }
    }
}

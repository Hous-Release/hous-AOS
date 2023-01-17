package hous.release.android.presentation.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityTutorialBinding
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.tutorial.adapter.TutorialAdapter
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.repeatOnStarted
import hous.release.domain.entity.TutorialEntity
import kotlin.system.exitProcess

@AndroidEntryPoint
class TutorialActivity : BindingActivity<ActivityTutorialBinding>(R.layout.activity_tutorial) {
    private lateinit var tutorialAdapter: TutorialAdapter
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = tutorialViewModel
        observeTutorialState()
        initAdapter()
        initSkipBtnOnClickListener()
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@TutorialActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    private fun observeTutorialState() {
        repeatOnStarted {
            tutorialViewModel.isTutorialState.collect { skip ->
                if (skip) {
                    intentToLogin()
                }
            }
        }
    }

    private fun intentToLogin() {
        val toLogin = Intent(this, LoginActivity::class.java)
        startActivity(toLogin)
        finish()
    }

    private fun initAdapter() {
        tutorialAdapter = TutorialAdapter()
        tutorialAdapter.submitList(tutorialList)
        binding.vpTutorial.adapter = tutorialAdapter
        binding.vpTutorial.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tlTutorialDot, binding.vpTutorial) { tab, _ ->
            tab.view.isClickable = false
        }.attach()

        binding.vpTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tutorialViewModel.isNextBtn.value = position == 3
            }
        })
    }

    private fun initSkipBtnOnClickListener() {
        binding.tvTutorialSkip.setOnClickListener {
            binding.vpTutorial.currentItem = 3
        }
    }

    companion object {
        private val tutorialList = listOf(
            TutorialEntity(
                R.string.tutorial_1_head,
                R.string.tutorial_1_body
            ),
            TutorialEntity(
                R.string.tutorial_2_head,
                R.string.tutorial_2_body
            ),
            TutorialEntity(
                R.string.tutorial_3_head,
                R.string.tutorial_3_body
            ),
            TutorialEntity(
                R.string.tutorial_4_head,
                R.string.tutorial_4_body
            )
        )
        private const val WAITING_DEADLINE = 2000L
    }
}

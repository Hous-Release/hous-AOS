package hous.release.android.presentation.tutorial

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import hous.release.android.R
import hous.release.android.databinding.ActivityTutorialBinding
import hous.release.android.presentation.tutorial.adapter.TutorialAdapter
import hous.release.android.util.binding.BindingActivity
import hous.release.domain.entity.TutorialEntity

class TutorialActivity : BindingActivity<ActivityTutorialBinding>(R.layout.activity_tutorial) {
    private lateinit var tutorialAdapter: TutorialAdapter
    private lateinit var tutorialList: List<TutorialEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initSkipBtnOnClickListener()
    }

    private fun initAdapter() {
        tutorialList = mutableListOf(
            TutorialEntity(
                R.string.tutorial_1_head,
                R.string.tutorial_1_body,
                R.drawable.shape_blue_fill_16_rect
            ),
            TutorialEntity(
                R.string.tutorial_2_head,
                R.string.tutorial_2_body,
                R.drawable.shape_red_fill_10_rect
            ),
            TutorialEntity(
                R.string.tutorial_3_head,
                R.string.tutorial_3_body,
                R.drawable.shape_blue_fill_16_rect
            ),
            TutorialEntity(
                R.string.tutorial_4_head,
                R.string.tutorial_4_body,
                R.drawable.shape_red_fill_10_rect
            )
        )

        tutorialAdapter = TutorialAdapter()
        tutorialAdapter.submitList(tutorialList)
        binding.vpTutorial.adapter = tutorialAdapter
        binding.vpTutorial.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tlTutorialDot, binding.vpTutorial) { tab, position ->
            tab.view.isClickable = false
        }.attach()

        binding.vpTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 3) showBtnNext()
                else hideBtnNext()
            }
        })
    }

    private fun initSkipBtnOnClickListener() {
        binding.tvTutorialSkip.setOnClickListener {
            binding.vpTutorial.currentItem = 3
        }
    }

    private fun showBtnNext() {
        binding.tlTutorialDot.visibility = View.INVISIBLE
        binding.tvTutorialNext.visibility = View.VISIBLE
    }

    private fun hideBtnNext() {
        binding.tlTutorialDot.visibility = View.VISIBLE
        binding.tvTutorialNext.visibility = View.INVISIBLE
    }
}

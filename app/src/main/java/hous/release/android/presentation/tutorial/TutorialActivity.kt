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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    private fun initAdapter() {
        tutorialAdapter = TutorialAdapter()
        tutorialAdapter.submitList(tutorialList)
        binding.vpTutorial.adapter = tutorialAdapter
        binding.vpTutorial.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }
}

package hous.release.android.presentation.hous

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentHousBinding
import hous.release.android.presentation.hous.adapter.HomiesAdapter
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class HousFragment : BindingFragment<FragmentHousBinding>(R.layout.fragment_hous) {
    private val viewModel by viewModels<HousViewModel>()
    private val homiesAdapter = HomiesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.getHome()
        initClickListener()
        initHomiesAdapter()
        initHomiesObserver()
    }

    private fun initClickListener() {
        binding.btnHousEdit.setOnClickListener {
            startActivity(Intent(requireContext(), EditHousNameActivity::class.java))
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
}

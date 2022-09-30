package hous.release.android.presentation.hous

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentHousBinding
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class HousFragment : BindingFragment<FragmentHousBinding>(R.layout.fragment_hous) {
    private val viewModel by viewModels<HousViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.getHome()
    }
}

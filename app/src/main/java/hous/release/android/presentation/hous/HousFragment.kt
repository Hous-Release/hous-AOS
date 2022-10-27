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
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.showToast

@AndroidEntryPoint
class HousFragment : BindingFragment<FragmentHousBinding>(R.layout.fragment_hous) {
    private val viewModel by viewModels<HousViewModel>()
    private val homiesAdapter = HomiesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initClickListener()
        initHomiesAdapter()
        initHomiesObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHome()
    }

    private fun initClickListener() {
        binding.btnHousEdit.setOnClickListener {
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
            requireContext().showToast(getString(R.string.hous_toast_copy))
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
    }
}

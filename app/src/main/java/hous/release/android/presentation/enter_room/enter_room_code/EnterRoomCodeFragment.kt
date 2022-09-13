package hous.release.android.presentation.enter_room.enter_room_code

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomCodeBinding
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class EnterRoomCodeFragment :
    BindingFragment<FragmentEnterRoomCodeBinding>(R.layout.fragment_enter_room_code) {
    private val viewModel by viewModels<EnterRoomCodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
        binding.viewModel = viewModel
    }

    private fun initBackBtnClickListener() {
        binding.btnEnterRoomCodeBack.setOnClickListener { findNavController().popBackStack() }
    }
}

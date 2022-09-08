package hous.release.android.presentation.enter_room.enter_room_code

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomCodeBinding
import hous.release.android.util.binding.BindingFragment

class EnterRoomCodeFragment :
    BindingFragment<FragmentEnterRoomCodeBinding>(R.layout.fragment_enter_room_code) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnEnterRoomCodeBack.setOnClickListener { findNavController().popBackStack() }
    }
}

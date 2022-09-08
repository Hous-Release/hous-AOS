package hous.release.android.presentation.enter_room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentCreateRoomBinding
import hous.release.android.util.binding.BindingFragment

class CreateRoomFragment :
    BindingFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnCreateRoomBack.setOnClickListener { findNavController().popBackStack() }
    }
}

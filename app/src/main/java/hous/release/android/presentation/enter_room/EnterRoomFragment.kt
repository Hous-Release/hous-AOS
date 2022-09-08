package hous.release.android.presentation.enter_room

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomBinding
import hous.release.android.util.binding.BindingFragment

class EnterRoomFragment : BindingFragment<FragmentEnterRoomBinding>(R.layout.fragment_enter_room) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCreateRoomBtnClickListener()
        initEnterRoomCodeBtnClickListener()
    }

    private fun initCreateRoomBtnClickListener() {
        binding.layoutEnterRoomCreate.setOnClickListener {
            findNavController().navigate(R.id.action_enterRoomFragment_to_createRoomFragment)
        }
    }

    private fun initEnterRoomCodeBtnClickListener() {
        binding.layoutEnterRoomEnterCode.setOnClickListener {
            findNavController().navigate(R.id.action_enterRoomFragment_to_enterRoomCodeFragment)
        }
    }
}

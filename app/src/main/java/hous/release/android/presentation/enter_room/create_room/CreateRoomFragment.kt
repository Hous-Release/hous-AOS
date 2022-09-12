package hous.release.android.presentation.enter_room.create_room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentCreateRoomBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import timber.log.Timber

@AndroidEntryPoint
class CreateRoomFragment :
    BindingFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room) {
    private val viewModel by viewModels<CreateRoomViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initBackBtnClickListener()
        initDoneBtnClickListener()
        initNewRoomCollector()
    }

    private fun initBackBtnClickListener() {
        binding.btnCreateRoomBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initDoneBtnClickListener() {
        binding.btnCreateRoomDone.setOnClickListener {
            viewModel.postCreateRoom()
        }
    }

    private fun initNewRoomCollector() {
        repeatOnStarted {
            viewModel.newRoom.collect { newRoom ->
                // TODO 방 이름 추가 후 다이얼로그 띄우기
                Timber.tag("EnterRoom - createRoom").d(newRoom.roomCode)
            }
        }
    }
}

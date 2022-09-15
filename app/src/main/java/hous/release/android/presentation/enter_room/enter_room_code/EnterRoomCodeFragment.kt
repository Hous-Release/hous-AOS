package hous.release.android.presentation.enter_room.enter_room_code

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomCodeBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class EnterRoomCodeFragment :
    BindingFragment<FragmentEnterRoomCodeBinding>(R.layout.fragment_enter_room_code) {
    private val viewModel by activityViewModels<EnterRoomCodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initBackBtnClickListener()
        initIsSuccessGetRoomCollector()
    }

    private fun initBackBtnClickListener() {
        binding.btnEnterRoomCodeBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initIsSuccessGetRoomCollector() {
        repeatOnStarted {
            viewModel.isSuccessGetRoom.filter { it }.collect {
                if (viewModel.roomInfo.value.roomId != null && viewModel.roomInfo.value.roomId != -1) {
                    EnterRoomCodeDialogFragment().show(parentFragmentManager, this.javaClass.name)
                }
            }
        }
    }
}

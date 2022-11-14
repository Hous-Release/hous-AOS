package hous.release.android.presentation.enter_room.enter_room_code

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentEnterRoomCodeBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.showToast

@AndroidEntryPoint
class EnterRoomCodeFragment :
    BindingFragment<FragmentEnterRoomCodeBinding>(R.layout.fragment_enter_room_code) {
    private val viewModel by activityViewModels<EnterRoomCodeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
        initIsSuccessGetRoomCollector()
        initIsFullCapacityCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetRoomCode()
    }

    private fun initEditTextClearFocus() {
        binding.layoutEnterRoomCode.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEnterRoomCodeBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initIsSuccessGetRoomCollector() {
        repeatOnStarted {
            viewModel.isSuccessGetRoom.collect { isSuccess ->
                if (isSuccess && viewModel.roomInfo.value.roomId != null && viewModel.roomInfo.value.roomId != -1) {
                    EnterRoomCodeDialogFragment().show(childFragmentManager, this.javaClass.name)
                }
            }
        }
    }

    private fun initIsFullCapacityCollector() {
        repeatOnStarted {
            viewModel.isFullCapacity.collect { isFull ->
                if (isFull) {
                    requireContext().showToast(getString(R.string.enter_room_code_full_capacity))
                }
            }
        }
    }
}

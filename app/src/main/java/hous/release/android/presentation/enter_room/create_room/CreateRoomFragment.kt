package hous.release.android.presentation.enter_room.create_room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentCreateRoomBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.repeatOnStarted
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
class CreateRoomFragment :
    BindingFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room) {
    private val viewModel by activityViewModels<CreateRoomViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initBackBtnClickListener()
        initIsSuccessCreateRoomCollector()
    }

    private fun initBackBtnClickListener() {
        binding.btnCreateRoomBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initIsSuccessCreateRoomCollector() {
        repeatOnStarted {
            viewModel.isSuccessCreateRoom.filter { isSuccess -> isSuccess }.collect {
                CreateRoomDialogFragment().show(parentFragmentManager, this.javaClass.name)
            }
        }
    }
}

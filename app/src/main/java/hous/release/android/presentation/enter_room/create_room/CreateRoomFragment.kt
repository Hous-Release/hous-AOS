package hous.release.android.presentation.enter_room.create_room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentCreateRoomBinding
import hous.release.android.util.KeyBoardUtil
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.domain.entity.RequestState

@AndroidEntryPoint
class CreateRoomFragment :
    BindingFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room) {
    private val viewModel by activityViewModels<CreateRoomViewModel>()
    private val loadingDialog = LoadingDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initEditTextClearFocus()
        initBackBtnClickListener()
        initCreateRoomRequestStateCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetRoomName()
    }

    private fun initEditTextClearFocus() {
        binding.layoutCreateRoom.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnCreateRoomBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initCreateRoomRequestStateCollector() {
        repeatOnStarted {
            viewModel.createRoomRequestState.collect { requestState ->
                when (requestState) {
                    RequestState.LOADING -> {
                        loadingDialog.show(childFragmentManager, LoadingDialogFragment.TAG)
                    }
                    RequestState.SUCCESS -> {
                        loadingDialog.dismiss()
                        CreateRoomDialogFragment().show(childFragmentManager, this.javaClass.name)
                    }
                    RequestState.FAILED -> {
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }
}

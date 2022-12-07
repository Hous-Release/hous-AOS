package hous.release.android.presentation.enter_room.enter_room_code

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import hous.release.android.R
import hous.release.android.databinding.DialogEnterRoomCodeBinding
import hous.release.android.presentation.main.MainActivity
import hous.release.android.util.dialog.LoadingDialogFragment
import hous.release.android.util.extension.repeatOnStarted
import hous.release.domain.entity.RequestState

class EnterRoomCodeDialogFragment : DialogFragment() {
    private var _binding: DialogEnterRoomCodeBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val viewModel by activityViewModels<EnterRoomCodeViewModel>()
    private val loadingDialog = LoadingDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_enter_room_code,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        initLayout()
        initEnterRoomRequestStateCollector()
        initCloseClickListener()
    }

    private fun initLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.77).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_white_fill_8_rect)
            }
        }
    }

    private fun initEnterRoomRequestStateCollector() {
        repeatOnStarted {
            viewModel.enterRoomRequestState.collect { requestState ->
                when (requestState) {
                    RequestState.LOADING -> {
                        loadingDialog.show(childFragmentManager, LoadingDialogFragment.TAG)
                    }
                    RequestState.SUCCESS -> {
                        loadingDialog.dismiss()
                        dismiss()
                        requireActivity().finish()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                    }
                    RequestState.FAILED -> {
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun initCloseClickListener() {
        binding.btnEnterRoomCodeClose.setOnClickListener { dismiss() }
    }
}

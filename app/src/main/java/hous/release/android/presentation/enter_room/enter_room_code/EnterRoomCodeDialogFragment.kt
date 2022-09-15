package hous.release.android.presentation.enter_room.enter_room_code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import hous.release.android.R
import hous.release.android.databinding.DialogEnterRoomCodeBinding

class EnterRoomCodeDialogFragment : DialogFragment() {
    private var _binding: DialogEnterRoomCodeBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val viewModel by activityViewModels<EnterRoomCodeViewModel>()

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
}
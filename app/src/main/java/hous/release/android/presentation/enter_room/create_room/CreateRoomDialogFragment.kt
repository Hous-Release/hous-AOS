package hous.release.android.presentation.enter_room.create_room

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import hous.release.android.R
import hous.release.android.databinding.DialogCreateRoomBinding
import hous.release.android.presentation.main.MainActivity

class CreateRoomDialogFragment : DialogFragment() {
    private var _binding: DialogCreateRoomBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val viewModel by activityViewModels<CreateRoomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_create_room,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initInviteBtnClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().finish()
        startActivity(Intent(requireContext(), MainActivity::class.java))
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

    private fun initInviteBtnClickListener() {
        binding.btnCreateRoomDialogInvite.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.newRoomInfo.roomCode)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}

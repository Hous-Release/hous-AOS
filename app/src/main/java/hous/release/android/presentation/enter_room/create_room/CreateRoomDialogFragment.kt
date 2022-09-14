package hous.release.android.presentation.enter_room.create_room

import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogCreateRoomBinding

class CreateRoomDialogFragment : DialogFragment() {
    private var _binding: DialogCreateRoomBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))


}

package hous.release.android.presentation.enter_room.enter_room_code

import androidx.fragment.app.DialogFragment
import hous.release.android.R
import hous.release.android.databinding.DialogEnterRoomCodeBinding

class EnterRoomCodeDialogFragment : DialogFragment() {
    private var _binding: DialogEnterRoomCodeBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))
}

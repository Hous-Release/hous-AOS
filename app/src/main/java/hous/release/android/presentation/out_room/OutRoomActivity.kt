package hous.release.android.presentation.out_room

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityOutRoomBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class OutRoomActivity : BindingActivity<ActivityOutRoomBinding>(R.layout.activity_out_room) {
    private val viewModel by viewModels<OutRoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSettingsMyToDo()
    }
}

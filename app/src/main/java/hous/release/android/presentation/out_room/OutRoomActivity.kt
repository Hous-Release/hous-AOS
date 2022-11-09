package hous.release.android.presentation.out_room

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityOutRoomBinding
import hous.release.android.presentation.out_room.adapter.MyToDoAdapter
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class OutRoomActivity : BindingActivity<ActivityOutRoomBinding>(R.layout.activity_out_room) {
    private val viewModel by viewModels<OutRoomViewModel>()
    private val myToDoAdapter = MyToDoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.getSettingsMyToDo()
        initMyToDoAdapter()
        initMyToDoCollector()
    }

    private fun initMyToDoAdapter() {
        binding.rvOutRoomMyToDo.adapter = myToDoAdapter
    }

    private fun initMyToDoCollector() {
        repeatOnStarted {
            viewModel.myToDos.collect { toDos ->
                myToDoAdapter.submitList(toDos)
            }
        }
    }
}

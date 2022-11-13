package hous.release.android.presentation.out_room

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityOutRoomBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
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
        initBackBtnClickListener()
        initMyToDoAdapter()
        initMyToDoCollector()
        initIsSuccessDeleteRoomCollector()
    }

    private fun initBackBtnClickListener() {
        binding.btnOutRoomBack.setOnClickListener { finish() }
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

    private fun initIsSuccessDeleteRoomCollector() {
        repeatOnStarted {
            viewModel.isSuccessDeleteRoom.collect { isSuccess ->
                if (isSuccess) {
                    startActivity(
                        Intent(this, EnterRoomActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                    )
                }
            }
        }
    }
}

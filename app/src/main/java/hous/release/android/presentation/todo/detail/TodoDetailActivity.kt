package hous.release.android.presentation.todo.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityTodoDetailBinding
import hous.release.android.util.HousFloatingButton
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class TodoDetailActivity :
    BindingActivity<ActivityTodoDetailBinding>(R.layout.activity_todo_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBarColor()
        initFloatingButton()
    }

    private fun initStatusBarColor() {
        window?.statusBarColor = ContextCompat.getColor(this, R.color.hous_g_1)
    }

    private fun initClickListener() {
//        binding.ivMemberBackButton.setOnClickListener {
//            finish()
//        }
    }

    private fun initFloatingButton() {
        binding.cvMemberFloatingButton.setContent {
            HousFloatingButton {
                /* TO DO 추가하기 뷰로 이동하는 함수 */
            }
        }
    }
}

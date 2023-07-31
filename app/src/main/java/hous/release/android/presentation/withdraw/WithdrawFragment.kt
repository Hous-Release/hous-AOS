package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawBinding
import hous.release.android.util.binding.BindingFragment
import hous.release.android.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class WithdrawFragment : BindingFragment<FragmentWithdrawBinding>(R.layout.fragment_withdraw) {
    private val withdrawViewModel by activityViewModels<WithdrawViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = withdrawViewModel
        initWithdrawDoneClickListener()
        initBackBtnClickListener()
    }

    /** TODO 영주 : 클릭리스너 없애고 데바로 탈퇴하기 api 연결 후 옵저버로 넘기는거 구현 */
    private fun initWithdrawDoneClickListener() {
        binding.tvWithdrawDone.setOnSingleClickListener {
            findNavController().navigate(R.id.action_withdrawFragment_to_feedbackFragment)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawBack.setOnClickListener {
            requireActivity().finish()
        }
    }
}
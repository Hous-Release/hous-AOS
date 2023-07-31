package hous.release.android.presentation.withdraw

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentWithdrawBinding
import hous.release.android.util.binding.BindingFragment

@AndroidEntryPoint
class WithdrawFragment : BindingFragment<FragmentWithdrawBinding>(R.layout.fragment_withdraw) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_withdrawFragment_to_feedbackFragment)
        }

        binding.back.setOnClickListener {
            requireActivity().finish()
        }
    }
}
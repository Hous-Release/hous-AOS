package hous.release.android.presentation.personality.test

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import hous.release.android.R
import hous.release.android.databinding.FragmentPersonalityTestStartBinding
import hous.release.android.util.binding.BindingFragment

class PersonalityTestStartFragment :
    BindingFragment<FragmentPersonalityTestStartBinding>(R.layout.fragment_personality_test_start) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTestStart.setOnClickListener {
            findNavController().navigate(R.id.action_personalityTestStartFragment_to_personalityTestFragment)
        }
    }
}

package hous.release.android.presentation.personality.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import hous.release.android.R
import hous.release.android.databinding.FragmentPersonalityTestBinding
import hous.release.android.presentation.personality.result.PersonalityResultActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.RESULT
import hous.release.android.util.binding.BindingFragment

class PersonalityTestFragment :
    BindingFragment<FragmentPersonalityTestBinding>(R.layout.fragment_personality_test) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goPersonalityTestResult()
    }

    private fun goPersonalityTestResult() {
        Intent(requireActivity(), PersonalityResultActivity::class.java).apply {
            putExtra(LOCATION, RESULT)
        }.also { intent ->
            requireActivity().finish()
            startActivity(intent)
        }
    }
}

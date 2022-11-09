package hous.release.android.presentation.personality.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.FragmentPersonalityTestBinding
import hous.release.android.presentation.personality.result.PersonalityResultActivity
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.LOCATION
import hous.release.android.presentation.personality.result.PersonalityResultActivity.Companion.RESULT
import hous.release.android.util.binding.BindingFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PersonalityTestFragment :
    BindingFragment<FragmentPersonalityTestBinding>(R.layout.fragment_personality_test) {
    private val personalityTestViewModel: PersonalityTestViewModel by viewModels()
    private lateinit var personalityTestAdapter: PersonalityTestAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        collectPersonalityTests()
        collectMoveEvent()
    }

    private fun initAdapter() {
        personalityTestAdapter = PersonalityTestAdapter(
            setTestState = personalityTestViewModel::setTestState,
            movePage = personalityTestViewModel::movePage
        )
        binding.vpPersonalityTest.apply {
            adapter = personalityTestAdapter
            isUserInputEnabled = false
        }
    }

    private fun collectPersonalityTests() {
        personalityTestViewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { personalityTestUiState ->
                personalityTestAdapter.submitList(personalityTestUiState.personalityTests)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectMoveEvent() {
        personalityTestViewModel.moveEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { move ->
                binding.vpPersonalityTest.currentItem += move
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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

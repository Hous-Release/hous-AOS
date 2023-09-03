package hous.release.android.presentation.our_rules.event

import hous.release.android.presentation.our_rules.model.RepresentRuleUiModel
import hous.release.android.presentation.our_rules.viewmodel.RepresentRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.RepresentRulesState
import hous.release.android.util.event.Reducer
import javax.inject.Inject

class RepresentRulesReducer @Inject constructor() :
    Reducer<RepresentRulesState, RepresentRulesEvent> {
    override fun dispatch(
        state: RepresentRulesState,
        event: RepresentRulesEvent
    ): RepresentRulesState {
        return when (event) {
            is RepresentRulesEvent.FetchRules -> {
                state.copy(
                    originRules = event.rules.map { RepresentRuleUiModel.from(it) },
                    rules = event.rules.map { RepresentRuleUiModel.from(it) }
                )
            }

            is RepresentRulesEvent.UpdateRule -> {
                state.copy(
                    rules = state.rules.map { rule ->
                        if (rule.id == event.id) rule.copy(isRepresent = rule.isRepresent.not())
                        else rule
                    }
                )
            }
        }
    }
}

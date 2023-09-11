package hous.release.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hous.release.android.presentation.our_rules.event.AddRuleReducer
import hous.release.android.presentation.our_rules.event.MainRuleReducer
import hous.release.android.presentation.our_rules.event.RepresentRulesReducer
import hous.release.android.presentation.our_rules.event.UpdateRuleReducer
import hous.release.android.presentation.our_rules.viewmodel.AddRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.AddRuleState
import hous.release.android.presentation.our_rules.viewmodel.MainRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.MainRulesState
import hous.release.android.presentation.our_rules.viewmodel.RepresentRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.RepresentRulesState
import hous.release.android.presentation.our_rules.viewmodel.UpdateRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.UpdateRuleState
import hous.release.android.util.event.Reducer
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ReducerModule {
    @MainRules
    @Binds
    abstract fun bindMainRuleReducer(mainRuleReducer: MainRuleReducer): Reducer<MainRulesState, MainRulesEvent>

    @AddRule
    @Binds
    abstract fun bindAddRuleReducer(addRuleReducer: AddRuleReducer): Reducer<AddRuleState, AddRuleEvent>

    @UpdateRule
    @Binds
    abstract fun bindUpdateRuleReducer(updateRuleReducer: UpdateRuleReducer): Reducer<UpdateRuleState, UpdateRuleEvent>

    @RepresentRules
    @Binds
    abstract fun bindRepresentRulesReducer(representRulesReducer: RepresentRulesReducer): Reducer<RepresentRulesState, RepresentRulesEvent>
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RepresentRules

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainRules

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AddRule

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UpdateRule

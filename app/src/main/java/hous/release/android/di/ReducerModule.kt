package hous.release.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hous.release.android.presentation.our_rules.event.AddRuleReducer
import hous.release.android.presentation.our_rules.event.MainRuleReducer
import hous.release.android.presentation.our_rules.viewmodel.AddRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.AddRuleState
import hous.release.android.presentation.our_rules.viewmodel.MainRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.MainRulesState
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
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainRules

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AddRule
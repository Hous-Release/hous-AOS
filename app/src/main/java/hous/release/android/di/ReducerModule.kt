package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.android.presentation.our_rules.event.AddRuleReducer
import hous.release.android.presentation.our_rules.event.MainRuleReducer
import hous.release.android.presentation.our_rules.viewmodel.AddRuleEvent
import hous.release.android.presentation.our_rules.viewmodel.AddRuleState
import hous.release.android.presentation.our_rules.viewmodel.MainRulesEvent
import hous.release.android.presentation.our_rules.viewmodel.MainRulesState
import hous.release.android.util.event.Reducer
import hous.release.domain.usecase.search.SearchRuleUseCase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReducerModule {
    @MainRules
    @Singleton
    @Provides
    fun providesMainRuleReducer(searcher: SearchRuleUseCase): Reducer<MainRulesState, MainRulesEvent> =
        MainRuleReducer()

    @AddRule
    @Singleton
    @Provides
    fun provideAddRuleReducer(): Reducer<AddRuleState, AddRuleEvent> = AddRuleReducer()
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainRules

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AddRule

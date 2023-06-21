package hous.release.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.domain.usecase.search.matcher.RuleNameMatcher
import hous.release.domain.usecase.search.matcher.StringMatcher
import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import hous.release.domain.usecase.search.strategy.RuleNameMatchStrategy

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {
    @Binds
    abstract fun bindRuleNameMatcher(ruleNameMatcher: RuleNameMatcher): StringMatcher

    @Binds
    abstract fun bindRuleNameMatchStrategy(ruleNameMatchStrategy: MixedEnKrMatchStrategy): RuleNameMatchStrategy
}

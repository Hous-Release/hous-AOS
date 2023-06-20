package hous.release.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hous.release.domain.usecase.search.matcher.RuleNameMatcher
import hous.release.domain.usecase.search.matcher.StringMatcher
import hous.release.domain.usecase.search.strategy.MixedEnKrMatchStrategy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideRuleNameMatcher(): StringMatcher =
        RuleNameMatcher(MixedEnKrMatchStrategy())
}

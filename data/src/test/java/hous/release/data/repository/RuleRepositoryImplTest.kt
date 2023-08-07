package hous.release.data.repository

import com.google.common.truth.Truth.assertThat
import hous.release.data.datasource.RuleDataSource
import hous.release.data.entity.response.rule.MainRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import hous.release.domain.entity.rule.MainRule
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class RuleRepositoryImplTest {
    @RelaxedMockK
    lateinit var ruleDataSource: RuleDataSource

    private lateinit var defaultRuleRepository: RuleRepositoryImpl

    @BeforeEach
    fun setUp() {
        defaultRuleRepository = RuleRepositoryImpl(ruleDataSource, Dispatchers.IO)
    }

    @Test
    fun `MainRulesResponse 객체 에서 MainRule로 변환 하여 규칙들을 불러 온다`() = runTest {
        // given
        val expectedMainRules = listOf(
            MainRule(
                34,
                "dd123xd",
                "2023.03.16",
                true
            ),
            MainRule(
                35,
                "ㄷ슏슛ㄷ",
                "2023.05.05",
                false
            )
        )
        coEvery {
            ruleDataSource.fetchMainRules()
        } returns MainRulesResponse(
            rules = listOf(
                MainRuleResponse(
                    34,
                    "dd123xd",
                    "2023-03-16T17:19:42.158498",
                    true
                ),
                MainRuleResponse(
                    35,
                    "ㄷ슏슛ㄷ",
                    "2023-05-05T19:31:34.794815",
                    false
                )
            )
        )

        // when
        val actualRules = defaultRuleRepository.fetchMainRules()
        // then
        assertThat(actualRules).isEqualTo(expectedMainRules)
    }
}

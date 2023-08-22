package hous.release.data.service

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.MainRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
internal class RuleServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var ruleService: RuleService

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        ruleService = Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().serializeNulls().create())
        ).baseUrl(mockWebServer.url("")).build().create()
    }

    @Test
    fun `우리집 전체 규칙을 불러올 수 있다`() = runTest {
        // given
        val profileJson = File("src/test/res/rule/success_main_rules.json").readText()
        val fakeResponse = MockResponse().setBody(profileJson).setResponseCode(200)
        mockWebServer.enqueue(fakeResponse)
        val expectedResponse = BaseResponse(
            status = 200,
            success = true,
            message = "규칙 메인 페이지 조회 성공입니다.",
            data = MainRulesResponse(
                rules = listOf(
                    MainRuleResponse(
                        34,
                        "dd123xd",
                        true,
                        "2023-03-16T17:19:42.158498",
                        true
                    ),
                    MainRuleResponse(
                        35,
                        "ㄷ슏슛ㄷ",
                        false,
                        "2023-05-05T19:31:34.794815",
                        false
                    )
                )
            )
        )
        // when
        val actualResponse = ruleService.getMainRules()
        // then
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @Test
    fun `rule의 id를 통해 rule의 상세 내용을 불러올 수 있다`() = runTest {
        // given
        val detailRule = File("src/test/res/rule/success_detail_rule.json").readText()
        val fakeResponse = MockResponse().setBody(detailRule).setResponseCode(200)
        mockWebServer.enqueue(fakeResponse)
        val expectedResponse = BaseResponse(
            status = 200,
            success = true,
            message = "규칙 조회 성공입니다.",
            data = DetailRuleResponse(
                id = 34,
                name = "이준원",
                description = "ㅎㅇㅎㅇ",
                images = listOf(),
                updatedAt = "2023-03-16T17:19:42.158498"
            )
        )
        // when
        val actualResponse = ruleService.getDetailRuleBy(34)
        // then
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }
}

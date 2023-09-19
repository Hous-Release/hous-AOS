package hous.release.designsystem.util.single_event

import com.google.common.truth.Truth
import hous.release.testing.CoroutinesTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
internal class ThrottledDurationStrategyTest {

    @Test
    fun `마지막 이벤트가 발생한 지 300ms가 지나지 않고 추가 이벤트가 발생시 무시한다`() = runTest {
        // given
        val strategy = ThrottledDurationStrategy()
        var count = 0
        // when
        strategy.handle {
            count++
        }
        delay(301)
        strategy.handle {
            count++
        }
        // then
        Truth.assertThat(count).isEqualTo(1)
    }
}

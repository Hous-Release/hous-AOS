package hous.release.designsystem.util.single_event

import hous.release.testing.CoroutinesTestExtension
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
@ExtendWith(MockKExtension::class)
internal class SingleEventHandlerTest {

    @Test
    fun `이벤트를 처리할 때 내부적으로 SingleEventHandleStrategy의 handle을 호출 한다`() {
        // given
        val mockStrategy = mockk<SingleEventHandleStrategy>(relaxed = true)
        val handler = SingleEventHandler(mockStrategy)

        // when
        every { mockStrategy.handle(any()) } returns Unit
        handler.handle { }
        // then
        verify(exactly = 1) { handler.handle(any()) }
    }
}

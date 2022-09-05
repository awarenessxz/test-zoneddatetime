package com.example

import com.example.service.TestService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

@SpringBootTest
class TestApplicationTests {
	@Mock private lateinit var mockClock: Clock
	@InjectMocks private lateinit var testService: TestService

	@Test
	fun testService_withoutMock() {
		val result = testService.printZonedDatetime()
		Assertions.assertEquals(ZonedDateTime.now().toString(), result)
	}

	@Test
	fun testService_mockZonedDateTimeWithMockStatic() {
		val expectedZonedDateTime = ZonedDateTime.parse("2017-08-10T08:00:00.000Z")
		Mockito.mockStatic(ZonedDateTime::class.java).use { mockedZonedDateTime ->
			mockedZonedDateTime.`when`<ZonedDateTime> { ZonedDateTime.now() }.thenReturn(expectedZonedDateTime)
			val result = testService.printZonedDatetime()
			Assertions.assertEquals(expectedZonedDateTime.toString(), result)
		}
	}

	@Test
	fun testService_mockZonedDateTimeWithClock() {
		val expectedZonedDateTime = ZonedDateTime.parse("2017-08-10T08:00:00.000Z")
		val fixedClocked = Clock.fixed(expectedZonedDateTime.toInstant(), ZoneId.of("UTC"))
		Mockito.`when`(mockClock.instant()).thenReturn(fixedClocked.instant())
		Mockito.`when`(mockClock.zone).thenReturn(fixedClocked.zone)

		val result = testService.printZonedDatetimeWithClock()

		Assertions.assertEquals(expectedZonedDateTime.toString(), result)
	}
}

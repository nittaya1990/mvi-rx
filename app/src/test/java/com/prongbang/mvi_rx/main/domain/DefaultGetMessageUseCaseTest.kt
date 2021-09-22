package com.prongbang.mvi_rx.main.domain

import com.prongbang.mvi_rx.core.CoreTest
import org.junit.Test

class DefaultGetMessageUseCaseTest : CoreTest() {
	private val useCase by lazy { DefaultGetMessageUseCase() }

	@Test
	fun `Should return message when get message success`() {
		// Given
		val result = "Hello MVI Rx"

		// When
		val observer = useCase.execute(Unit)
				.test()

		// Then
		observer.assertValue(result)
	}
}
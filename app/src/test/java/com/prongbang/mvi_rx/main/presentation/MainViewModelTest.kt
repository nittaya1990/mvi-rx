package com.prongbang.mvi_rx.main.presentation

import com.prongbang.mvi_rx.core.CoreTest
import com.prongbang.mvi_rx.main.domain.GetMessageUseCase
import com.prongbang.mvi_rx.main.domain.MainIntent
import com.prongbang.mvi_rx.main.domain.MainState
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.Test

class MainViewModelTest : CoreTest() {

	@MockK
	private lateinit var getMessageUseCase: GetMessageUseCase

	private val viewModel by lazy { MainViewModel(getMessageUseCase) }

	@Test
	fun `Should return message when get message success`() {
		// Given
		val result = "Hello MVI Rx"
		val testObserverState = TestObserver.create<MainState>()
		every { getMessageUseCase.execute(Unit) } returns Observable.just(result)

		// When
		viewModel.stateSubscribe(testObserverState)
		viewModel.process(MainIntent.GetData)

		// Then
		testObserverState.assertValues(
				MainState.Result(result)
		)
	}
}
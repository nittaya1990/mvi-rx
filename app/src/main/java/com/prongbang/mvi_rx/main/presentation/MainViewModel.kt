package com.prongbang.mvi_rx.main.presentation

import com.prongbang.mvi_rx.main.domain.GetMessageUseCase
import com.prongbang.mvi_rx.main.domain.MainEffect
import com.prongbang.mvi_rx.main.domain.MainIntent
import com.prongbang.mvi_rx.main.domain.MainState
import com.prongbang.mvirx.RxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
		private val getMessageUseCase: GetMessageUseCase
) : RxViewModel<MainIntent, MainState, MainEffect>() {

	override fun process(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
		}
	}

	private fun processGetData() {
		getMessageUseCase.execute(Unit)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeBy(onNext = { result ->
					setState(MainState.Result(result))
				}, onError = {
					setEffect(MainEffect.Error("${it.message}"))
				})
				.addTo(compositeDisposable)
	}

}
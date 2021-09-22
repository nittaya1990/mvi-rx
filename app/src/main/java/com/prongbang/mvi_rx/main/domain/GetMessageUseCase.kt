package com.prongbang.mvi_rx.main.domain

import com.prongbang.mvirx.RxUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

abstract class GetMessageUseCase : RxUseCase<Unit, String>

class DefaultGetMessageUseCase @Inject constructor() : GetMessageUseCase() {
	override fun execute(params: Unit): Observable<String> {
		return Observable.fromCallable {
			"Hello MVI Rx"
		}
	}
}
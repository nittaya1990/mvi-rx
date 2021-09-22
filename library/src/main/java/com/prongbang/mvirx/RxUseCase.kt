package com.prongbang.mvirx

import io.reactivex.rxjava3.core.Observable

/**
 * How to use:
 * class NamedUseCase : RxUseCase<Unit, String> {
 *      override fun execute(params: Unit): Observable<String> {
 *          return Observable.fromCallable {
 *              "Hello MVI Rx"
 *          }
 *      }
 * }
 */
interface RxUseCase<Param, Result> {
	fun execute(params: Param): Observable<Result>
}
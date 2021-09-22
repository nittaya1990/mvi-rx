package com.prongbang.mvirx

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.PublishRelay
import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.jetbrains.annotations.TestOnly

/**
 * How to use:
 * class NamedViewModel : RxViewModel<FeatureIntent, FeatureState, FeatureEffect>() {
 *      override fun process(intent: FeatureIntent) {
 *          when (intent) {
 *              FeatureIntent.GetData -> processGetData()
 *          }
 *      }
 * }
 */
abstract class RxViewModel<I : RxIntent, S : RxState, E : RxEffect> : ViewModel() {
	protected val compositeDisposable = CompositeDisposable()
	private val states: Relay<S> = PublishRelay.create()
	private val effects: Relay<E> = PublishRelay.create()

	fun stateSubscribe(onResult: (S) -> Unit) {
		states.subscribe { onResult.invoke(it) }
				.addTo(compositeDisposable)
	}

	fun effectSubscribe(onResult: (E) -> Unit) {
		effects.subscribe { onResult.invoke(it) }
				.addTo(compositeDisposable)
	}

	fun subscribe(onState: (S) -> Unit, onEffect: (E) -> Unit) {
		stateSubscribe(onState)
		effectSubscribe(onEffect)
	}

	protected fun setState(state: S) {
		states.accept(state)
	}

	protected fun setEffect(effect: E) {
		effects.accept(effect)
	}

	@TestOnly
	fun stateSubscribe(observer: Observer<S>) {
		states.subscribe(observer)
	}

	@TestOnly
	fun effectSubscribe(observer: Observer<E>) {
		effects.subscribe(observer)
	}

	override fun onCleared() {
		super.onCleared()
		compositeDisposable.dispose()
	}

	abstract fun process(intent: I)
}
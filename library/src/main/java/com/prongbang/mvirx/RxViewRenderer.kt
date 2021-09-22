package com.prongbang.mvirx

/**
 * How to use:
 * class FeatureActivity : AppCompatActivity(), RxViewRenderer<FeatureState, FeatureEffect> {
 *      override fun render(state: FeatureState) {
 *
 *      }
 *
 *      override fun renderEffect(effect: FeatureEffect) {
 *
 *      }
 * }
 */
interface RxViewRenderer<S : RxState, E : RxEffect> {
	fun render(state: S)
	fun renderEffect(effect: E)
}
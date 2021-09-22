package com.prongbang.mvirx

/**
 * How to use:
 * sealed class FeatureState : RxState {
 *      object Idle : FeatureState()
 *      data class Result(val data: String) : FeatureState()
 * }
 */
interface RxState
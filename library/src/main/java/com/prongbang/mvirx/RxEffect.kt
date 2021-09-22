package com.prongbang.mvirx

/**
 * How to use:
 * sealed class FeatureEffect : RxEffect {
 *      object Idle : MainEffect()
 *      data class Error(val data: String) : MainEffect()
 * }
 */
interface RxEffect
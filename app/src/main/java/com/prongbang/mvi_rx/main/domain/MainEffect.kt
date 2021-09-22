package com.prongbang.mvi_rx.main.domain

import com.prongbang.mvirx.RxEffect

sealed class MainEffect : RxEffect {
	object Idle : MainEffect()
	data class Error(val data: String) : MainEffect()
}

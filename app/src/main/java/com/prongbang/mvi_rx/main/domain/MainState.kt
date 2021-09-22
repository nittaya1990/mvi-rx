package com.prongbang.mvi_rx.main.domain

import com.prongbang.mvirx.RxState

sealed class MainState : RxState {
	object Idle : MainState()
	data class Result(val data: String) : MainState()
}

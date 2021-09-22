package com.prongbang.mvi_rx.main.domain

import com.prongbang.mvirx.RxIntent

sealed class MainIntent : RxIntent {
	object GetData : MainIntent()
}

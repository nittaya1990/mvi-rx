package com.prongbang.mvi_rx.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.prongbang.mvi_rx.R
import com.prongbang.mvi_rx.main.domain.MainEffect
import com.prongbang.mvi_rx.main.domain.MainIntent
import com.prongbang.mvi_rx.main.domain.MainState
import com.prongbang.mvirx.RxViewRenderer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RxViewRenderer<MainState, MainEffect> {

	private val mainViewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		mainViewModel.subscribe(::render, ::renderEffect)

		mainViewModel.process(MainIntent.GetData)
	}

	override fun render(state: MainState) {
		when (state) {
			is MainState.Result -> {
				findViewById<TextView>(R.id.messageText).text = state.data
			}
		}
	}

	override fun renderEffect(effect: MainEffect) {
		when (effect) {
			is MainEffect.Error -> {
				findViewById<TextView>(R.id.messageText).text = effect.data
			}
		}
	}
}
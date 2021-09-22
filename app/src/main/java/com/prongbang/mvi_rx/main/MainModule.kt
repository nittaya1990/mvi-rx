package com.prongbang.mvi_rx.main

import com.prongbang.mvi_rx.main.domain.DefaultGetMessageUseCase
import com.prongbang.mvi_rx.main.domain.GetMessageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MainModule {

	@Binds
	abstract fun provideGetMessageUseCase(useCase: DefaultGetMessageUseCase): GetMessageUseCase
}
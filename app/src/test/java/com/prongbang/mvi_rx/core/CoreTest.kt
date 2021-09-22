package com.prongbang.mvi_rx.core

import com.prongbang.mvi_rx.core.rule.RxImmediateSchedulerRule
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import java.util.logging.Logger

abstract class CoreTest {
	open val logger = Logger.getLogger("CoreTest")

	@get:Rule
	open var rule = RxImmediateSchedulerRule()

	@Before
	open fun setUp() {
		MockKAnnotations.init(this, relaxUnitFun = true)
	}

}
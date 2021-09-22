package com.prongbang.mvi_rx.core.rule

import androidx.annotation.NonNull
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.schedulers.ExecutorScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * This error occurs because the default scheduler returned by AndroidSchedulers.mainThread() is an instance of LooperScheduler
 * https://stackoverflow.com/questions/43356314/android-rxjava-2-junit-test-getmainlooper-in-android-os-looper-not-mocked-runt
 */
class RxImmediateSchedulerRule : TestRule {
	private val immediate = object : Scheduler() {
		override fun scheduleDirect(@NonNull run: Runnable,
		                            delay: Long, @NonNull unit: TimeUnit): Disposable {
			// this prevents StackOverflowErrors when scheduling with a delay
			return super.scheduleDirect(run, 0, unit)
		}

		override fun createWorker(): Worker {
			return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true, true)
		}
	}

	override fun apply(base: Statement?, description: Description?): Statement {
		return object : Statement() {
			@Throws(Throwable::class)
			override fun evaluate() {
				RxJavaPlugins.setInitIoSchedulerHandler { immediate }
				RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
				RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
				RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
				RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

				try {
					base?.evaluate()
				} finally {
					RxJavaPlugins.reset()
					RxAndroidPlugins.reset()
				}
			}
		}
	}
}
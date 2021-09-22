# mvi-rx

[![](https://jitpack.io/v/prongbang/mvi-rx.svg)](https://jitpack.io/#prongbang/mvi-rx)

## Setup

- `build.gradle`

```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `settings.gradle`

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

- `app/build.gradle`

```groovy
implementation 'com.github.prongbang:mvi-rx:1.0.1'
```

## How to use

- MainIntent.kt

```kotlin
import com.prongbang.mvirx.RxIntent

sealed class MainIntent : RxIntent {
	object GetData : MainIntent()
}
```

- MainSate.kt

```kotlin
import com.prongbang.mvirx.RxState

sealed class MainState : RxState {
	object Idle : MainState()
	data class Result(val data: String) : MainState()
}
```

- MainEffect.kt

```kotlin
import com.prongbang.mvirx.RxEffect

sealed class MainEffect : RxEffect {
	object Idle : MainEffect()
	data class Error(val data: String) : MainEffect()
}
```

- GetMessageUseCase.kt

```kotlin
import com.prongbang.mvirx.RxUseCase
import io.reactivex.rxjava3.core.Observable

class GetMessageUseCase : RxUseCase<Unit, String> {
	override fun execute(params: Unit): Observable<String> {
		return Observable.fromCallable {
			"Hello MVI Rx"
		}
	}
}
```

- MainViewModel.kt

```kotlin
import com.prongbang.mvirx.RxViewModel

class MainViewModel(
		private val getMessageUseCase: GetMessageUseCase
) : RxViewModel<MainIntent, MainState, MainEffect>() {

	override fun process(intent: MainIntent) {
		when (intent) {
			MainIntent.GetData -> processGetData()
		}
	}

	private fun processGetData() {
		getMessageUseCase.execute(Unit)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeBy(onNext = { result ->
					setState(MainState.Result(result))
				}, onError = {
					setEffect(MainEffect.Error("${it.message}"))
				})
				.addTo(compositeDisposable)
	}
}
```

- MainActivity.kt

```kotlin
import com.prongbang.mvirx.RxViewRenderer

class MainActivity : AppCompatActivity(), RxViewRenderer<MainState, MainEffect> {

	private val mainViewModel: MainViewModel by viewModels()
    
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		mainViewModel.subscribe(::render, ::renderEffect)

		mainViewModel.process(MainIntent.GetData)
	}

	override fun initObserve() {
		mainViewModel.subscribe(::render, ::renderEffect)
	}

	private fun initLoad() {
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
```

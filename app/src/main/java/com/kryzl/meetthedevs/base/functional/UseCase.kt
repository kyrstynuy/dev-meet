package com.kryzl.meetthedevs.base.functional

import com.kryzl.meetthedevs.base.exception.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<Request, Response> {

    /**
     * IFF needed in the implemented [run] method,
     * [Request] must be provided prior to [invoke]
     */
    abstract var request: Request

    /**
     * This will determine whether the [UseCase]
     * should run on Main or IO coroutine context
     */
    abstract val coroutineContext: CoroutineContext

    enum class CoroutineContext {
        MAIN,
        IO
    }

    /**
     * By default, [UseCase] will do
     * nothing with the result
     */
    var onResult: (Either<Failure, Response>) -> Any = { }

    abstract suspend fun run()

    /**
     * [onResult] will be used as callback for result
     * provided on call to [setResult]
     */
    operator fun invoke(
        scope: CoroutineScope,
        coroutineContextProvider: CoroutineContextProvider,
        onResult: ((Either<Failure, Response>) -> Unit)? = null
    ) {
        onResult?.let {
            this.onResult = onResult
        }
        val backgroundJob = scope.async {
            withContext(
                when (this@UseCase.coroutineContext) {
                    CoroutineContext.MAIN -> coroutineContextProvider.Main
                    CoroutineContext.IO -> coroutineContextProvider.IO
                }
            ) { return@withContext run() }
        }
        scope.launch { backgroundJob.await() }
    }

    /**
     * Call [setResult] to trigger [onResult]
     * provided on invocation
     */
    protected fun setResult(result: Either<Failure, Response>) {
        onResult(result)
    }
}

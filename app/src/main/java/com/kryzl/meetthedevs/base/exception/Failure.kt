package com.kryzl.meetthedevs.base.exception

/**
 * Credits to Fernando Cejas
 * @see https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/interactor/UseCase.kt
 *
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    class NetworkConnection : Failure()
    class TimeoutError : Failure()
    data class ServerError(val message: String = "") : Failure()
}

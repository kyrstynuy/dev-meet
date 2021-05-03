package com.kryzl.meetthedevs.domain.remote

import com.kryzl.meetthedevs.base.exception.Failure
import com.kryzl.meetthedevs.base.functional.Either
import retrofit2.Call
import timber.log.Timber
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.net.*

abstract class RemoteDataSource {

    /**
     * Credits to Fernando Cejas
     * @link https://github.com/android10/Android-CleanArchitecture-Kotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/features/movies/MoviesRepository.kt
     */
    protected fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return handleException {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform(response.body() ?: default))
                false -> Either.Left(Failure.ServerError(response.errorBody()?.string() ?: ""))
            }
        }
    }

    private fun <R> handleException(function: () -> Either<Failure, R>): Either<Failure, R> {
        return try {
            function.invoke()
        } catch (exception: IOException) {
            handleIOException(exception)
        } catch (exception: RuntimeException) {
            handleRuntimeException(exception)
        } catch (exception: Exception) {
            handleGenericException(exception)
        }
    }

    private fun handleGenericException(exception: Exception): Either.Left<Failure.ServerError> {
        Timber.w("RemoteDataSource catch all exception is: ${printStackTrace(exception)}")
        return Either.Left(Failure.ServerError(exception.localizedMessage ?: ""))
    }

    private fun handleRuntimeException(exception: RuntimeException): Either.Left<Failure.ServerError> {
        Timber.w("RemoteDataSource remote exception is: ${printStackTrace(exception)}")
        return Either.Left(Failure.ServerError(exception.localizedMessage ?: ""))
    }

    private fun handleIOException(exception: IOException): Either.Left<Failure> {
        exception.printStackTrace()
        Timber.w("RemoteDataSource exception is: ${printStackTrace(exception)}")

        return Either.Left(
            when (exception) {
                is SocketTimeoutException -> Failure.TimeoutError()
                is UnknownHostException,
                is NoRouteToHostException,
                is ConnectException,
                is PortUnreachableException -> Failure.NetworkConnection()
                else -> Failure.ServerError(exception.localizedMessage ?: "")
            }
        )
    }

    private fun printStackTrace(exception: Exception) =
        StringWriter().also {
            exception.printStackTrace(PrintWriter(it))
        }.toString()

}

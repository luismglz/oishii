package com.arasaka.oishii.framework.api

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.core.platform.NetworkHandler
import retrofit2.Call
import java.lang.Exception

interface ApiRequest {
    /*
   T = Call type
   R = Request
    */

    //Left ->Failure
    //Right -> Success

    fun <T, R> makeRequest(
        networkHandler: NetworkHandler,
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> {
                try {
                    val response = call.execute();
                    when (response.isSuccessful) {
                        true -> Either.Right(transform(response.body() ?: default));
                        false -> Either.Left(Failure.ServerError(500, ""))
                    }
                } catch (ex: Exception) {
                    Either.Left(Failure.ServerError(500, ex.message))
                }
            }
            false -> Either.Left(Failure.NetworkConnection)
        }
    }
}
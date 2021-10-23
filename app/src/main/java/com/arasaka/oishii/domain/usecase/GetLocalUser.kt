package com.arasaka.oishii.domain.usecase

import com.arasaka.oishii.core.exception.Failure
import com.arasaka.oishii.core.functional.Either
import com.arasaka.oishii.core.interactor.UseCase
import com.arasaka.oishii.core.platform.AuthManager
import com.arasaka.oishii.domain.model.User
import javax.inject.Inject

class GetLocalUser@Inject constructor(private val authManager: AuthManager): UseCase<User, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, User> {
        val result = authManager.user
        return result?.let {
            Either.Right(it)//its done, return user

        }?: Either.Left(Failure.Unauthorized)//If user or auth is null..
    }
}
package com.longterm.artschools.data.repository

import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.models.RegisterRequest
import com.longterm.artschools.data.service.UserApi

class UserRepository(
    private val userApi: UserApi,
    private val userStorage: UserStorage
) {
    suspend fun register(
        email: String,
        password: String,
        repeatPassword: String,
        age: Int? = null,
        firstName: String? = null,
        photoUrl: String? = null,
    ): Result<Unit> {
        return userApi.register(
            RegisterRequest(
                email,
                password,
                repeatPassword,
                age,
                firstName,
                photoUrl
            )
        ).onSuccess { authResponse ->
            userStorage.token = authResponse.token

            userApi.me().onSuccess {
                userStorage.user = it
            }
        }.map { }
    }
}
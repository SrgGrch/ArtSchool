package com.longterm.artschools.data.repository

import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.api.UserApi
import com.longterm.artschools.data.models.RegisterRequest
import com.longterm.artschools.data.models.VkAuthRequest
import com.longterm.artschools.domain.models.User

class UserRepository(
    private val userApi: UserApi,
    private val userStorage: UserStorage
) {
    suspend fun register(
        email: String,
        password: String,
        repeatPassword: String,
        age: Int? = null,
        firstName: String? = null
    ): Result<Unit> {
        return userApi.register(
            RegisterRequest(
                email,
                password,
                repeatPassword,
                age,
                firstName
            )
        ).onSuccess { authResponse ->
            userStorage.token = authResponse.token
        }.map { }
    }

    suspend fun register(
        vkAuthToken: String,
        email: String
    ): Result<Unit> {
        return userApi.authWithVk(
            VkAuthRequest(
                vkAuthToken,
                email
            )
        ).onSuccess { authResponse ->
            userStorage.token = authResponse.token
        }.map { }
    }

    suspend fun updateUser(): Result<User> {
        return userApi.me().onSuccess {
            userStorage.user = it
        }
    }
}
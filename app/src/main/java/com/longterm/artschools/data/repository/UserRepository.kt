package com.longterm.artschools.data.repository

import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.api.UserApi
import com.longterm.artschools.data.models.account.AuthRequest
import com.longterm.artschools.data.models.account.RegisterRequest
import com.longterm.artschools.data.models.account.SetPreferencesRequest
import com.longterm.artschools.data.models.account.SetTargetsRequest
import com.longterm.artschools.data.models.account.VkAuthRequest
import com.longterm.artschools.domain.models.User
import com.longterm.artschools.ui.core.mapToUnit
import com.longterm.artschools.ui.core.onSuccessMap

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
        preferencesCodes: List<String>? = null,
        targetsCodes: List<String>? = null
    ): Result<Unit> {
        return userApi.register(
            RegisterRequest(
                email,
                password,
                repeatPassword
            )
        ).onSuccess { authResponse ->
            userStorage.token = authResponse.token
        }.onSuccessMap {
            userApi.updateMe(age = age, name = firstName)
        }.onSuccessMap {
            sendAdditionalData(preferencesCodes, targetsCodes)
        }
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
        }.mapToUnit()
    }

    suspend fun authorize(email: String, password: String): Result<Unit> {
        return userApi.authorize(
            AuthRequest(
                email,
                password
            )
        ).onSuccess { authResponse ->
            userStorage.token = authResponse.token
        }.mapToUnit()
    }

    suspend fun updateUser(): Result<User> {
        return userApi.me().onSuccess {
            userStorage.user = it
        }
    }

    fun signOut() {
        userStorage.user = null
        userStorage.token = null
    }

    suspend fun sendAdditionalData(
        preferencesCodes: List<String>? = null,
        targetsCodes: List<String>? = null
    ): Result<Unit> {
        val preferencesResult = preferencesCodes?.let { userApi.setPreferences(SetPreferencesRequest(it)) }
        val preferencesResultSuccess = preferencesResult?.isSuccess ?: true
        val targetsResult = targetsCodes?.let { userApi.setTargets(SetTargetsRequest(it)) }
        val targetsResultSuccess = targetsResult?.isSuccess ?: true

        return if (preferencesResultSuccess && targetsResultSuccess) Result.success(Unit)
        else Result.failure(
            preferencesResult?.exceptionOrNull() ?: targetsResult?.exceptionOrNull()
            ?: IllegalStateException("Both requests did not succeed, but no errors")
        )
    }


    suspend fun getUser(): Result<User> {
        return userApi.me()
            .onSuccess {
                userStorage.user = it
            }
    }

    suspend fun getUserNullable(): User? {
        return getUser().getOrNull() ?: return null
    }
}
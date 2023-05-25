package com.longterm.artschools.data.service

import com.longterm.artschools.data.UserStorage
import com.longterm.artschools.data.models.AuthRequest
import com.longterm.artschools.data.models.AuthResponse
import com.longterm.artschools.data.models.RegisterRequest
import com.longterm.artschools.domain.models.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserApi(
    private val httpClient: HttpClient,
    private val userStorage: UserStorage
) {
    suspend fun register(
        request: RegisterRequest
    ): BaseResponse<AuthResponse> = httpClient.post("register") {
        setBody(request)
    }.body()

    suspend fun authorize(request: AuthRequest): BaseResponse<AuthResponse> = httpClient.post("signin") {
        setBody(request)
    }.body()

    suspend fun me() = httpClient.get("me") {
        headers {
            append("Authorization", userStorage.token ?: error("No token"))
        }
    }
}
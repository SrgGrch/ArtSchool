package com.longterm.artschools.data.service

import com.longterm.artschools.data.models.AuthRequest
import com.longterm.artschools.data.models.AuthResponse
import com.longterm.artschools.data.models.BaseResponse
import com.longterm.artschools.data.models.RegisterRequest
import com.longterm.artschools.domain.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserApi(
    private val httpClient: HttpClient
) {
    suspend fun register(
        request: RegisterRequest
    ): Result<AuthResponse> = httpClient.post("register/") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body<BaseResponse<AuthResponse>>().unwrap()

    suspend fun authorize(request: AuthRequest): BaseResponse<AuthResponse> = httpClient.post("signin/") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

    suspend fun me(): Result<User> = httpClient.get("me/") {
        withAuth()
    }.body<BaseResponse<User>>().unwrap()
}
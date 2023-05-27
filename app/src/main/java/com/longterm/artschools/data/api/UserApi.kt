package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.AuthRequest
import com.longterm.artschools.data.models.AuthResponse
import com.longterm.artschools.data.models.RegisterRequest
import com.longterm.artschools.data.models.SetPreferencesRequest
import com.longterm.artschools.data.models.SetTargetsRequest
import com.longterm.artschools.data.models.VkAuthRequest
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
    ): Result<AuthResponse> = httpClient.withResultUnwrapped {
        post("register/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun authorize(request: AuthRequest): Result<AuthResponse> = httpClient.withResultUnwrapped {
        post("signin/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun authWithVk(
        request: VkAuthRequest
    ): Result<AuthResponse> = httpClient.withResultUnwrapped {
        post("vk_signin/") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun setPreferences(
        request: SetPreferencesRequest
    ): Result<List<String>> = httpClient.withResultUnwrapped {
        post("set_preferences/") {
            withAuth()
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun setTargets(
        request: SetTargetsRequest
    ): Result<List<String>> = httpClient.withResultUnwrapped {
        post("set_targets/") {
            withAuth()
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun me(): Result<User> = httpClient.withResultUnwrapped {
        get("me/") {
            withAuth()
        }.body()
    }
}
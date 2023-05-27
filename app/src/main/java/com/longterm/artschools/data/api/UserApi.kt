package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.account.AuthRequest
import com.longterm.artschools.data.models.account.AuthResponse
import com.longterm.artschools.data.models.account.RegisterRequest
import com.longterm.artschools.data.models.account.SetPreferencesRequest
import com.longterm.artschools.data.models.account.SetTargetsRequest
import com.longterm.artschools.data.models.account.VkAuthRequest
import com.longterm.artschools.domain.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.parameters

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
        put("set_preferences/") {
            withAuth()
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun setTargets(
        request: SetTargetsRequest
    ): Result<List<String>> = httpClient.withResultUnwrapped {
        put("set_targets/") {
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

    suspend fun updateMe(
        name: String? = null,
        age: Int? = null,
        email: String? = null
    ): HttpResponse = httpClient.submitForm {
        url(path = "update_me/")
        parameters {
            name?.let { append("first_name", it) }
            age?.let { append("email", it.toString()) }
            email?.let { append("age", it) }
        }
        withAuth()
    }


    suspend fun updateMe(
        name: String? = null,
        age: Int? = null,
        email: String? = null,
        avatar: ByteArray? = null
    ): HttpResponse = httpClient.submitFormWithBinaryData(
        formData = formData {
            name?.let { append("first_name", it) }
            age?.let { append("email", it.toString()) }
            email?.let { append("age", it) }

            avatar?.let {
                append("avatar", it, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=image.png")
                })
            }
        }
    ) {
        url(path = "update_me")
        onUpload { bytesSentTotal, contentLength ->
            println("Sent $bytesSentTotal bytes from $contentLength")
        }
        withAuth()
    }
}
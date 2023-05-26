package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResult
import com.longterm.artschools.data.models.VkAuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class VkApi(private val httpClient: HttpClient) {
    suspend fun getAccessToken(
        code: String,
        clientId: String,
        clientSecret: String
    ): Result<VkAuthResponse> = httpClient.withResult {
        get {
            url {
                url("https://oauth.vk.com/access_token")
                parameters.append("code", code)
                parameters.append("client_id", clientId)
                parameters.append("client_secret", clientSecret)
                parameters.append("redirect_uri", "https://oauth.vk.com/blank.html")
            }
            url
        }.body()
    }
}
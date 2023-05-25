package com.longterm.artschools.data.network

import com.longterm.artschools.data.UserStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory(
    private val userStorage: UserStorage,
    private val json: Json
) {
    fun create(baseUrl: String) = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL

            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        defaultRequest {
            url(urlString = baseUrl)
        }
    }
        .apply {
            plugin(HttpSend).intercept { request ->
                val headers = request.headers
                if (headers["Authorization"] != null) {
                    headers["Authorization"] = userStorage.token?.let {
                        "Bearer $it"
                    } ?: error("No auth token")
                }

                execute(request)
            }
        }
}
package com.longterm.artschools.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    operator fun invoke(json: Json, baseUrl: String? = null) = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json)

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL

                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }

            install(DefaultRequest) {
                url(baseUrl)
            }
        }
    }
}
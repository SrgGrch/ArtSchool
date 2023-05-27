package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.domain.models.Preference
import com.longterm.artschools.domain.models.Target
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OnboardingApi(
    private val httpClient: HttpClient
) {
    suspend fun targets(): Result<List<Target>> = httpClient
        .withResultUnwrapped {
            get("target/").body()
        }

    suspend fun preferences(): Result<List<Preference>> = httpClient
        .withResultUnwrapped {
            get("preferences/").body()
        }
}
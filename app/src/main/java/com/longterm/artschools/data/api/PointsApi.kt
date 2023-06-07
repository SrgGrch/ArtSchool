package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.points.PointsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PointsApi(
    private val httpClient: HttpClient
) {
    suspend fun getPoints(query: String? = null): Result<List<PointsResponse>> = httpClient.withResultUnwrapped {
        val path = query?.let { "point/$query" } ?: "point/"
        get(path) {
            withAuth()
        }.body()
    }
}
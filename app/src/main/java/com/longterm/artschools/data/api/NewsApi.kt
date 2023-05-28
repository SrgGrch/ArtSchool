package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.news.ArticleResponse
import com.longterm.artschools.data.models.news.ArticleTagResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NewsApi(
    private val httpClient: HttpClient
) {
    suspend fun getAllNews(): Result<List<ArticleResponse>> = httpClient.withResultUnwrapped {
        get("news/") {
            withAuth()
        }.body()
    }

    suspend fun getArticle(id: Int): Result<ArticleResponse> = httpClient.withResultUnwrapped {
        get("news/$id") {
            withAuth()
        }.body()
    }

    suspend fun getTags(): Result<List<ArticleTagResponse>> = httpClient.withResultUnwrapped {
        get("news/tags") {
            withAuth()
        }.body()
    }
}


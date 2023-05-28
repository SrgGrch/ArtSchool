package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.quiz.PostAnswerRequest
import com.longterm.artschools.data.models.quiz.PostAnswerResponse
import com.longterm.artschools.data.models.quiz.QuizRecordResponse
import com.longterm.artschools.data.models.quiz.QuizResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class QuizApi(
    private val httpClient: HttpClient
) {
    suspend fun getQuizzes(): Result<List<QuizRecordResponse>> = httpClient.withResultUnwrapped {
        get("quiz/") {
            withAuth()
        }.body()
    }

    suspend fun getQuiz(id: Int): Result<QuizResponse> = httpClient.withResultUnwrapped {
        get("quiz/$id") {
            withAuth()
        }.body()
    }

    suspend fun postAnswer(id: Int, request: Int): Result<PostAnswerResponse> = httpClient.withResultUnwrapped {
        post("quiz/$id/answer") {
            withAuth()
            contentType(ContentType.Application.Json)
            setBody(PostAnswerRequest(listOf(request)))
        }.body()
    }
}




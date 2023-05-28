package com.longterm.artschools.data.api

import com.longterm.artschools.data.api.core.withResultUnwrapped
import com.longterm.artschools.data.models.courses.CourseResponse
import com.longterm.artschools.data.models.courses.CoursesListResponse
import com.longterm.artschools.data.models.lesson.LessonResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CoursesApi(private val httpClient: HttpClient) {
    suspend fun getAll(): Result<List<CoursesListResponse>> = httpClient.withResultUnwrapped {
        get("course/") {
            contentType(ContentType.Application.Json)
            withAuth()
        }.body()
    }

    suspend fun get(id: Int): Result<CourseResponse> = httpClient.withResultUnwrapped {
        get("course/$id") {
            contentType(ContentType.Application.Json)
            withAuth()
        }.body()
    }

    suspend fun getLesson(id: Int): Result<LessonResponse> = httpClient.withResultUnwrapped {
        get("course/lesson/$id") {
            contentType(ContentType.Application.Json)
            withAuth()
        }.body()
    }
}
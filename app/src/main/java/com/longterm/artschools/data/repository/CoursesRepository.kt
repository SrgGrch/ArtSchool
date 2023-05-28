package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.CoursesApi
import com.longterm.artschools.data.models.courses.CourseResponse
import com.longterm.artschools.data.models.courses.CoursesListResponse

class CoursesRepository(
    private val coursesApi: CoursesApi
) {
    suspend fun getAll(): Result<List<CoursesListResponse>> = coursesApi.getAll()
    suspend fun get(id: Int): Result<CourseResponse> = coursesApi.get(id)
}
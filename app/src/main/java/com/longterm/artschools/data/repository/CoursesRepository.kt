package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.CoursesApi
import com.longterm.artschools.data.models.courses.CourseResponse
import com.longterm.artschools.data.models.courses.CoursesListResponse
import com.longterm.artschools.data.models.lesson.LessonResponse

class CoursesRepository(
    private val coursesApi: CoursesApi
) {
    suspend fun getAll(): Result<List<CoursesListResponse>> = coursesApi.getAll()
    suspend fun get(id: Int): Result<CourseResponse> = coursesApi.get(id)
    suspend fun getLesson(id: Int): Result<LessonResponse> = coursesApi.getLesson(id)
}
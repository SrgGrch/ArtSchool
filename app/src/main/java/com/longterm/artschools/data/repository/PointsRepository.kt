package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.PointsApi
import com.longterm.artschools.domain.models.LatLng
import com.longterm.artschools.domain.models.points.Point

class PointsRepository(
    private val pointsApi: PointsApi
) {
    var lastPoint: Point? = null
        private set

    suspend fun getPoints(query: String? = null): Result<List<Point>> = pointsApi.getPoints(query).map { result ->
        result.map {
            with(it) {
                Point(
                    LatLng(latitude, longitude),
                    name,
                    description,
                    "Академическая (Ленинский просп., 45)",
                    "+74952128506",
                    workTime,
                    preferences
                )
            }
        }
    }

    fun savePoint(point: Point) {
        lastPoint = point
    }
}
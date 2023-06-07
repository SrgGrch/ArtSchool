package com.longterm.artschools.domain.models.points

import com.longterm.artschools.domain.models.LatLng

data class Point(
    val latLng: LatLng,
    val name: String,
    val description: String,
    val address: String,
    val phoneNumber: String?,
    val workTime: String
)
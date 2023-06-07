package com.longterm.artschools.ui.components.map

import android.content.Context
import android.graphics.PointF
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.map.Map as YandexMap

object MapUtils {
    private const val DEFAULT_ZOOM = 15f
    private const val ZOOM_STEP_DEFAULT_MAP = 2
    private val ZOOM_ANIMATION = Animation(Animation.Type.LINEAR, 0.25f)
    private val SMOOTH_MOVE_ANIMATION = Animation(Animation.Type.SMOOTH, 2f)

    val ICON_STYLE_POINT_AS_PIN_SELECTED: IconStyle = IconStyle().setAnchor(PointF(0.5f, 1f))
    val ICON_STYLE_POINT_AS_PIN: IconStyle = IconStyle().setAnchor(PointF(0.5f, 0.5f))

    fun YandexMap.zoomIn() {
        val cp = cameraPosition
        move(CameraPosition(cp.target, cp.zoom + ZOOM_STEP_DEFAULT_MAP, 0f, 0f), ZOOM_ANIMATION, null)
    }

    fun YandexMap.zoomOut() {
        val cp = cameraPosition
        move(
            CameraPosition(cp.target, cp.zoom - ZOOM_STEP_DEFAULT_MAP, 0f, 0f),
            ZOOM_ANIMATION,
            null
        )
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    fun YandexMap.moveToMyLocation(context: Context, zoom: Float = DEFAULT_ZOOM) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                move(
                    CameraPosition(Point(location.latitude, location.longitude), zoom, 0f, 0f),
                    SMOOTH_MOVE_ANIMATION, null
                )
            }
        }
    }

    fun getCurrentLocation(
        userLocationLayer: UserLocationLayer?,
        lat: Double = 0.0,
        lng: Double = 0.0
    ): CameraPosition {
        val cameraPosition = userLocationLayer?.cameraPosition()
        return cameraPosition ?: CameraPosition(Point(lat, lng), 0f, 0f, 0f)
    }
}
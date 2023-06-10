package com.longterm.artschools.ui.components.map

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.longterm.artschools.MainActivity
import com.longterm.artschools.R
import com.longterm.artschools.domain.models.points.Point
import com.longterm.artschools.ui.components.common.SearchToolbar
import com.longterm.artschools.ui.components.map.MapUtils.moveToMyLocation
import com.longterm.artschools.ui.components.map.MapUtils.zoomIn
import com.longterm.artschools.ui.components.map.MapUtils.zoomOut
import com.longterm.artschools.ui.core.theme.ArtTextStyle
import com.longterm.artschools.ui.core.theme.Colors
import com.longterm.artschools.ui.core.utils.PreviewContext
import com.longterm.artschools.ui.navigation.destination.BottomBarDestination
import com.longterm.artschools.ui.navigation.destination.BottomSheetDestinations
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.compose.getViewModel
import kotlin.collections.Map
import kotlin.math.roundToInt
import com.yandex.mapkit.geometry.Point as YandexPoint
import com.yandex.mapkit.map.Map as YandexMap


@SuppressLint("MissingPermission") // todo
@Composable
fun MapScreen(
    openPointInfo: (Point) -> Unit,
    currentDestination: String
) {
    val context = LocalContext.current

    var permissionsGranted by remember {
        mutableStateOf(false)
    }

    val launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissionsGranted = it.all { e -> e.value }
        }

    context.requestPermissions(launcher) {
        permissionsGranted = true
    }

    val vm: MapVm = getViewModel()
    val state by vm.state.collectAsState()

    var listeners = remember { mutableListOf<MapObjectTapListener>() }

    var objects by remember { mutableStateOf<MapObjectCollection?>(null) }
    var shouldCenter by rememberSaveable { mutableStateOf(true) }

    val showPoint = (state as? MapVm.State.Data)?.showPoint
    if (showPoint != null)
        LaunchedEffect(key1 = state) {
            openPointInfo(showPoint)
        }

    if (currentDestination == BottomSheetDestinations.MapPointInfo.route) {
        vm.onPointShown()
    }

    if (currentDestination == BottomBarDestination.Map.route && showPoint == null) {
        vm.clearSelection()
    }

    var map: YandexMap? = null
    var userLocationLayer: UserLocationLayer? by remember {
        mutableStateOf(null)
    }

    Box {
        Column {
            SearchToolbar(
                title = {
                    Text(text = "Школы на карте", Modifier.padding(start = 16.dp), style = ArtTextStyle.H3)
                },
                searchValue = (state as? MapVm.State.Data)?.searchQuery ?: "",
                onSearchValueChanged = vm::onSearchValueChanged,
            )
            Box {
                AndroidView(factory = {
                    MapView(it)
                }) { mapView ->
                    val lifecycleOwner: LifecycleOwner = (mapView.context as MainActivity)

                    val observer = LifecycleEventObserver { _, event -> // todo move outside
                        when (event) {
                            Lifecycle.Event.ON_START -> {
                                mapView.onStart()
                            }

                            Lifecycle.Event.ON_STOP -> {
                                mapView.onStop()
                            }

                            else -> {}
                        }
                    }

                    lifecycleOwner.lifecycle.addObserver(observer)

                    map = mapView.map

                    mapView.map.isRotateGesturesEnabled = false
                    mapView.map.isTiltGesturesEnabled = false

                    if (shouldCenter && permissionsGranted) {
                        MapKitFactory.getInstance().resetLocationManagerToDefault()
                    }

                    if (shouldCenter) {
                        map?.moveToMyLocation(context)

                        shouldCenter = false
                    }

                    if (permissionsGranted) {
                        if (userLocationLayer == null) userLocationLayer =
                            MapKitFactory.getInstance().createUserLocationLayer(mapView.mapWindow)
                        userLocationLayer?.isVisible = true
                    }

                    val st = state
                    if (objects == null)
                        objects = mapView.map.mapObjects.addCollection()

                    listeners = mutableListOf()
                    objects?.clear()

                    if (st is MapVm.State.Data) {
                        st.points.forEach {
                            val mapObject = objects?.addPlacemark(
                                YandexPoint(it.point.latLng.latitude, it.point.latLng.longitude),
                            )

                            mapObject?.addTapListener(MapObjectTapListener { _, _ ->
                                vm.onPointClicked(it)

                                true
                            }.also(listeners::add))

                            if (it.selected) {

                                mapObject?.setIcon(
                                    ImageProvider.fromResource(
                                        mapView.context,
                                        R.drawable.ic_map_point_selected
                                    ),
                                    MapUtils.ICON_STYLE_POINT_AS_PIN_SELECTED
                                )
                            } else {
                                mapObject?.setIcon(
                                    ImageProvider.fromResource(
                                        mapView.context,
                                        R.drawable.ic_map_point,
                                    ),
//                                    ImageProvider.fromBitmap(
//                                        createPin(it.point.preferences),
//                                        true,
//                                        it.point.latLng.toString()
//                                    ),
                                    MapUtils.ICON_STYLE_POINT_AS_PIN
                                )
                            }
                        }
                    }
                }

                val scrollState = rememberScrollState()
                val st = state
                if (st is MapVm.State.Data) {
                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(top = 6.dp)
                            .horizontalScroll(scrollState)
                    ) {
                        st.filters.forEach { filter ->
                            FilterChip(
                                selected = filter.isSelected,
                                onClick = { vm.onFilterClick(filter.id) },
                                label = {
                                    Text(text = filter.text, style = ArtTextStyle.Chips)
                                },
                                shape = CircleShape,
                                modifier = Modifier.padding(horizontal = 4.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Colors.GreenMain,
                                    containerColor = Color.White
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = Colors.GreenMain,
                                    borderWidth = 2.dp
                                )
                            )

                        }
                    }
                }
            }
        }

        if (permissionsGranted) {
            Column(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                IconButton(
                    onClick = { map?.zoomIn() },
                    Modifier
                        .clip(CircleShape)
                        .shadow(elevation = 4.dp)
                        .background(Color.White)
                ) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = { map?.zoomOut() },
                    Modifier
                        .clip(CircleShape)
                        .shadow(elevation = 4.dp)
                        .background(Color.White)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_zoom_out),
                        contentDescription = null,
                        Modifier.padding(6.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = {
                        map?.moveToMyLocation(context)
                    },
                    Modifier
                        .clip(CircleShape)
                        .shadow(elevation = 4.dp)
                        .background(Color.White)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_location),
                        contentDescription = null,
                        Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private inline fun Context.requestPermissions(
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    block: () -> Unit
) {
    val finePermissionCheckResult = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
    val coarsePermissionCheckResult = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
    if (finePermissionCheckResult == PackageManager.PERMISSION_GRANTED
        && coarsePermissionCheckResult == PackageManager.PERMISSION_GRANTED
    ) {
        block()
    } else {
        SideEffect {
            // Request a permission
            launcher.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    PreviewContext {
        Row(
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Школы на карте", style = ArtTextStyle.H3)

            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = "Поиск")
            }
        }
    }
}

private val pins = mutableMapOf<Int, Bitmap>()

private fun createPin(preferences: List<Int>): Bitmap {
    val id = preferences.first()
    return pins[id] ?: run {
        textAsBitmap(getText(id), 70f).also {
            pins[id] = it
        }
    }
}

fun getText(id: Int) = when (id) {
    1 -> "✍️"
    2 -> "🎭"
    3 -> "🎨"
    4 -> "🩰"
    else -> "🎶"
}

fun textAsBitmap(text: String, textSize: Float): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = textSize
    paint.textAlign = Paint.Align.LEFT
    val baseline: Float = -paint.ascent() // ascent() is negative
    val width = (paint.measureText(text) + 0.5f).roundToInt() // round
    val height = (baseline + paint.descent() + 0.5f).roundToInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    canvas.drawCircle(0f, baseline, 25f, Paint().apply {
        color = 0xFFFFFF
    })
    return image
}
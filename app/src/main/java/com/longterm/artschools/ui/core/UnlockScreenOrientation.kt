package com.longterm.artschools.ui.core

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun UnlockScreenOrientation() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = SCREEN_ORIENTATION_USER
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

@Composable
fun SwitchScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    LaunchedEffect(orientation) {
        val activity = context.findActivity() ?: return@LaunchedEffect
        activity.requestedOrientation = orientation
    }
//    DisposableEffect(orientation) {
//        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
//        val originalOrientation = activity.requestedOrientation
//        activity.requestedOrientation = orientation
//        onDispose {
//            // restore original orientation when view disappears
//            activity.requestedOrientation = originalOrientation
//        }
//    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

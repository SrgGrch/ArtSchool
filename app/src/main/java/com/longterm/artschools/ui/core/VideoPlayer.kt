package com.longterm.artschools.ui.core

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.longterm.artschools.ui.core.theme.Colors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1.87f)
        .background(Colors.Black)
) {
    val context = LocalContext.current

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = modifier,
            update = {
                it.setShowNextButton(false)
                it.setShowPreviousButton(false)
            }
        ),
    ) {
        val observer = LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }

                Lifecycle.Event.ON_RESUME -> Unit

                else -> Unit
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
//            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    }
}

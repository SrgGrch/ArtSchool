package com.longterm.artschools.ui.components

import android.app.Activity
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.longterm.artschools.R
import com.longterm.artschools.service.PlaybackService

@UnstableApi
class PlayerActivity : Activity() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null

    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.player_view)
    }

    override fun onStart() {
        super.onStart()
        initializeController()
    }

    override fun onStop() {
        super.onStop()
        playerView.player = null
        releaseController()
    }

    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                this,
                SessionToken(this, ComponentName(this, PlaybackService::class.java))
            ).buildAsync()

        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun releaseController() {
        MediaController.releaseFuture(controllerFuture)
    }

    private fun setController() {
        val controller = this.controller ?: return
        playerView.player = controller
        playerView.setShowSubtitleButton(false)
        playerView.setShowShuffleButton(false)

        val a = DashMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(
                MediaItem.Builder()
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setIsPlayable(true)
                            .build()
                    )
                    .setUri(
                        Uri.parse("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd")
                    )
                    .build()
            )

        controller.setMediaItem(
            MediaItem.Builder()
//                .setMimeType(MimeTypes.APPLICATION_MPD)
                .setUri(
                    Uri.parse("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd")
                )
                .build()
        )
//        controller.setMediaSource()
        controller.playWhenReady = true
        controller.prepare()
//        controller.play()
    }
}
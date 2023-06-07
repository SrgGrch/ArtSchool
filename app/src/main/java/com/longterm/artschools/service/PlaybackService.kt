/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.longterm.artschools.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.*
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.AudioAttributes
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.*
import androidx.media3.session.MediaSession.ControllerInfo
import com.google.common.util.concurrent.ListenableFuture
import com.longterm.artschools.MainActivity
import com.longterm.artschools.R
import com.longterm.artschools.ui.components.PlayerActivity

@UnstableApi
class PlaybackService : MediaSessionService() {
    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession

    companion object {
        private const val NOTIFICATION_ID = 123
        private const val CHANNEL_ID = "art_schools_courses"
    }

    override fun onCreate() {
        super.onCreate()
        initializeSessionAndPlayer()
        setListener(MediaSessionServiceListener())
    }

    override fun onGetSession(controllerInfo: ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (!player.playWhenReady) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()
        clearListener()
        super.onDestroy()
    }

    private fun initializeSessionAndPlayer() {
        player =
            ExoPlayer.Builder(this)
                .setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus = */ true)
                .build()

        player.addAnalyticsListener(EventLogger())

        val sessionActivityPendingIntent =
            TaskStackBuilder.create(this).run {
                addNextIntent(Intent(this@PlaybackService, MainActivity::class.java))
                addNextIntent(Intent(this@PlaybackService, PlayerActivity::class.java))

                getPendingIntent(0, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
            }

        mediaSession =
            MediaSession.Builder(this, player)
                .setSessionActivity(sessionActivityPendingIntent)
                .build()
    }

    private fun ignoreFuture(customLayout: ListenableFuture<SessionResult>) {
        /* Do nothing. */
    }

    private inner class MediaSessionServiceListener : Listener {

        /**
         * This method is only required to be implemented on Android 12 or above when an attempt is made
         * by a media controller to resume playback when the {@link MediaSessionService} is in the
         * background.
         */
        @SuppressLint("MissingPermission") // TODO: Request this permission at runtime.
        override fun onForegroundServiceStartNotAllowedException() {
            val notificationManagerCompat = NotificationManagerCompat.from(this@PlaybackService)
            ensureNotificationChannel(notificationManagerCompat)
            val pendingIntent =
                TaskStackBuilder.create(this@PlaybackService).run {
                    addNextIntent(Intent(this@PlaybackService, MainActivity::class.java))

                    val immutableFlag = FLAG_IMMUTABLE
                    getPendingIntent(0, immutableFlag or FLAG_UPDATE_CURRENT)
                }

            val builder =
                NotificationCompat.Builder(this@PlaybackService, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(androidx.media3.session.R.drawable.media3_notification_small_icon)
                    .setContentTitle("Обратно в приложение")
                    .setStyle(
                        NotificationCompat.BigTextStyle().bigText("Вернуться в приложение")
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)

            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun ensureNotificationChannel(notificationManagerCompat: NotificationManagerCompat) {
        if (Util.SDK_INT < 26 || notificationManagerCompat.getNotificationChannel(CHANNEL_ID) != null) {
            return
        }

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_channel_video),
                NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationManagerCompat.createNotificationChannel(channel)
    }
}

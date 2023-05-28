package com.longterm.artschools.data.repository

import com.longterm.artschools.domain.models.Playlist
import com.longterm.artschools.domain.models.Tag

class PlaylistRepository {
    fun getPlaylist() = listOf(
        Playlist(
            1,
            "https://vk.com/music/playlist/-117742128_84942446_d28fc5ffcfe8793c4f",
            "https://cdn-icons-png.flaticon.com/512/4091/4091355.png",
            "То, что слышал каждый!",
            "Собрали плейлист с 21 самыми известными произведениями классических композиторов",
            listOf(
                Tag("Интервью", "#FF8E75A8"),
                Tag("🎶 Музыка", "#FFDBB0C2"),
            )
        )
    )
}
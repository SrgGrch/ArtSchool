package com.longterm.artschools.ui.components.lesson

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.longterm.artschools.data.models.lesson.LessonResponse
import com.longterm.artschools.data.repository.CoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LessonViewModel(
    private val id: Int,
    private val exoPlayer: ExoPlayer,
    private val coursesRepository: CoursesRepository
) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        get(id)
    }

    fun retry() {
        get(id)
    }

    private fun get(id: Int) {
        viewModelScope.launch {
            _state.update {
                coursesRepository.getLesson(id).getOrNull()?.let {
                    State.Data(
                        it,
                        player = exoPlayer.apply {
                            val mediaItem = MediaItem.Builder()
                                .setUri(
                                    Uri.parse("https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd")
                                )
                                .build()

                            setMediaItem(mediaItem)
                            prepare()
                        }
                    )
                } ?: State.Error
            }
        }
    }

    override fun onCleared() {
        exoPlayer.release()
    }

    sealed interface State {
        object Loading : State
        object Error : State
        data class Data(val lesson: LessonResponse, val player: ExoPlayer? = null) : State
    }
}

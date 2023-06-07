package com.longterm.artschools.ui.components.lesson

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.longterm.artschools.domain.models.lesson.Lesson
import com.longterm.artschools.domain.usecase.LessonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LessonViewModel(
    private val id: Int,
    private val exoPlayer: ExoPlayer,
    private val lessonUseCase: LessonUseCase
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
                lessonUseCase.execute(id).getOrNull()?.let {
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
        data class Data(val lesson: Lesson, val player: ExoPlayer? = null) : State
    }
}

package com.longterm.artschools.ui.components.lesson

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.longterm.artschools.domain.models.lesson.Lesson
import com.longterm.artschools.domain.usecase.AnswerQuizUseCase
import com.longterm.artschools.domain.usecase.LessonUseCase
import com.longterm.artschools.ui.components.main.models.MainListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LessonViewModel(
    private val id: Int,
    private val exoPlayer: ExoPlayer,
    private val lessonUseCase: LessonUseCase,
    private val answerQuizUseCase: AnswerQuizUseCase,
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

    fun onQuizAnswer(answer: MainListItem.QuizItem.Answer, quizId: Int, position: Int) {
        viewModelScope.launch {
            answerQuizUseCase.execute(quizId, answer.id)
                .onSuccess { result ->
                    _state.update { state ->
                        (state as? State.Data)?.let {
                            val items = it.lesson.quizes.toMutableList()
                            val quiz = items[position]
                            items[position] = quiz.copy(
                                isCorrectAnswerSelected = result.correct,
                                answers = quiz.answers.map { answerItem ->
                                    if (answerItem.id == answer.id) {
                                        answerItem.copy(selected = true)
                                    } else answerItem
                                },
                                answerDescription = result.reaction
                            )

                            it.copy(lesson = it.lesson.copy(quizes = items))
                        } ?: state
                    }
                }
                .onFailure {
                    Log.e("LessonViewModel", it.stackTraceToString())
                }
        }
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

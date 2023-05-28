package com.longterm.artschools.ui.components.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.domain.usecase.AnswerQuizUseCase
import com.longterm.artschools.domain.usecase.GetFeedUseCase
import com.longterm.artschools.ui.components.main.models.MainListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val answerQuizUseCase: AnswerQuizUseCase
) : ViewModel() {
    val state: StateFlow<State>
        get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        loadData()
    }

    fun onQuizAnswer(answer: MainListItem.QuizItem.Answer, quizId: Int, position: Int) {
        viewModelScope.launch {
            answerQuizUseCase.execute(quizId, answer.id)
                .onSuccess { result ->
                    _state.update { state ->
                        (state as? State.Data)?.let {
                            val items = it.items.toMutableList()
                            val quiz = items[position] as? MainListItem.QuizItem ?: return@let state
                            items[position] = quiz.copy(
                                isCorrectAnswerSelected = result.correct,
                                answers = quiz.answers.map { answerItem ->
                                    if (answerItem.id == answer.id) {
                                        answerItem.copy(selected = true)
                                    } else answerItem
                                },
                                answerDescription = result.reaction
                            )

                            it.copy(items = items)
                        } ?: state
                    }
                }
        }
    }

    fun retry() {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        getFeedUseCase.execute()
            .onSuccess { list ->
                _state.update {
                    State.Data(list, "3", null)
                }
            }
            .onFailure {
                Log.e("MainViewModel", it.stackTraceToString())
                _state.update {
                    State.Error
                }
            }
    }

    sealed interface State {
        val userPic: String?
            get() = null

        val level: String
            get() = "·"

        object Loading : State
        object Error : State
        data class Data(
            val items: List<MainListItem>,
            override val level: String,
            override val userPic: String?,
            val isLoading: Boolean = false,
        ) : State
    }
}
package com.longterm.artschools.ui.components.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longterm.artschools.data.repository.NewsRepository
import com.longterm.artschools.domain.models.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticleVm(
    private val id: Int,
    private val newsRepository: NewsRepository
) : ViewModel() {
    val state: StateFlow<State> get() = _state
    private val _state = MutableStateFlow<State>(State.Loading)

    init {
        loadData()
    }

    fun retry() {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        if (id == -1) return@launch _state.update {
            State.WTF
        }

        newsRepository.getArticle(id)
            .onSuccess { article ->
                _state.update {
                    State.Data(
                        article.id,
                        article.title,
                        article.text,
                        "https://static.tildacdn.com/tild6338-3363-4137-b534-663038303161/DSCF0696.jpg", // todo, // todo
                        3, // todo
                        article.tags
                    )
                }
            }
            .onFailure {
                Log.e("ArticleVm", it.stackTraceToString())
                _state.update {
                    State.Error
                }
            }
    }

    sealed interface State {
        object Loading : State
        object WTF : State
        object Error : State
        data class Data(
            val id: Int,
            val title: String,
            val description: String,
            val imageUrl: String,
            val reward: Int,
            val tags: List<Tag>
        ) : State
    }
}
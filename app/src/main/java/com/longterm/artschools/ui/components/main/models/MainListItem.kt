package com.longterm.artschools.ui.components.main.models

sealed interface MainListItem {
    data class QuizItem(
        val id: Int,
        val reward: Int,
        val title: String,
        val description: String,
        val imageUrl: String,
        val answers: List<Answer>,
        val answerDescription: String? = null
    ) : MainListItem {
        data class Answer(
            val text: String,
            val correct: Boolean,
            val selected: Boolean = false
        )
    }

    data class ArticleItem(
        val id: Int,
        val title: String,
        val description: String,
        val imageUrl: String,
        val reward: Int,
        val tags: List<Tag>
    ) : MainListItem {
        data class Tag(
            val text: String,
            val color: Long
        )
    }
}
package com.longterm.artschools.domain.usecase

import com.longterm.artschools.data.repository.NewsRepository
import com.longterm.artschools.data.repository.PlaylistRepository
import com.longterm.artschools.data.repository.QuizRepository
import com.longterm.artschools.ui.components.main.models.MainListItem
import com.longterm.artschools.ui.core.withResult

class GetFeedUseCase(
    private val newsRepository: NewsRepository,
    private val quizRepository: QuizRepository,
    private val playlistsRepository: PlaylistRepository
) {
    suspend fun execute(): Result<List<MainListItem>> {
        return withResult {
            val quizzes = quizRepository.getQuizzes()
                .getOrThrow()
                .map {
                    quizRepository.getQuiz(it.id).getOrThrow()
                }
                .filter { it.userAnswers == null }
                .shuffled()
                .map {
                    MainListItem.QuizItem(
                        it.id,
                        (1..3).random(),//todo
                        it.question,
                        it.text,
                        it.image,//todo
                        it.answers.map { a ->
                            MainListItem.QuizItem.Answer(
                                a.id,
                                a.text
                            )
                        }
                    )
                }

            val playlists = playlistsRepository.getPlaylist()
                .map {
                    MainListItem.VkPlaylist(
                        it.linkToVk,
                        it.imageUrl,
                        it.title,
                        it.description,
                        it.tags.map { (text, color) -> MainListItem.Tag(text, color) },
                    )
                }

            val news = newsRepository.getAllNews()
                .getOrThrow()
                .map {
                    MainListItem.ArticleItem(
                        it.id,
                        it.title,
                        it.text, // todo
                        it.image,
                        (1..3).random(), // todo
                        it.tags.map { (text, color) -> MainListItem.Tag(text, color) }
                    )
                }

            buildList {
                val qi = quizzes.iterator()
                if (qi.hasNext()) {
                    add(qi.next())
                }

                addAll(playlists)

                news.forEachIndexed { index, articleItem ->
                    add(articleItem)

                    if (index % 3 == 0 && index != 0) {
                        if (qi.hasNext()) {
                            add(qi.next())
                        }
                    }
                }
            }
        }
    }
}
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
                .map {
                    MainListItem.QuizItem(
                        it.id,
                        3,//todo
                        it.question,
                        it.text,
                        "https://avatars.mds.yandex.net/get-mpic/4262452/img_id5635830207981014623.jpeg/orig",//todo
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
                        "https://static.tildacdn.com/tild6338-3363-4137-b534-663038303161/DSCF0696.jpg", // todo
                        3, // todo
                        it.tags.map { (text, color) -> MainListItem.Tag(text, color) }
                    )
                }

            buildList {
                addAll(quizzes)
                addAll(playlists)
                addAll(news)
            }
        }
    }
}
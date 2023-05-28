package com.longterm.artschools.data.repository

import com.longterm.artschools.data.api.NewsApi
import com.longterm.artschools.data.models.news.ArticleResponse
import com.longterm.artschools.domain.models.Tag
import com.longterm.artschools.domain.models.news.Article

class NewsRepository(
    private val newsApi: NewsApi,
) {
    private var tags: Map<Int, Tag> = emptyMap()

    suspend fun getAllNews(): Result<List<Article>> = newsApi.getAllNews().map { res ->
        res.map {
            it.asArticle()
        }
    }

    suspend fun getArticle(id: Int): Result<Article> = newsApi.getArticle(id).map { it.asArticle(id) }

    suspend fun getTags(): Result<List<Tag>> = newsApi.getTags().map { tags ->
        tags.map {
            Tag(
                it.title,
                it.color,
                it.id
            )
        }
    }

    private suspend fun getTagsMap(): Map<Int, Tag> {
        if (tags.isEmpty()) {
            val list = getTags().getOrNull() ?: emptyList()

            tags = list.associateBy { it.id }
        }

        return tags
    }

    private suspend fun ArticleResponse.asArticle(_id: Int? = null): Article {
        val tagList = getTagsMap()
        return Article(
            id ?: _id!!,
            title,
            text,
            tags.mapNotNull {
                tagList[it]
            }
        )
    }
}


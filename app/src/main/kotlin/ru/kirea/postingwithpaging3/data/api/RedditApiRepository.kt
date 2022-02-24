package ru.kirea.postingwithpaging3.data.api

import ru.kirea.postingwithpaging3.data.entities.PostData
import ru.kirea.postingwithpaging3.data.entities.ResponseRoot

class RedditApiRepository(private val apiRequests: ApiRequests): ApiRepository {

    /**
     * Загрузить список постов.
     * @param after уникальный id поста, после которого нужно получить новый список постов.
     * @param before уникальный id поста, до которого нужно получить новый список постов.
     * @return список постов [PostData] или null, если нет постов.
     */
    override suspend fun getPosts(after: String?, before: String?): List<PostData> {
        val result: ResponseRoot = apiRequests.getPosts(after = after, before = before)
        return result.data.children.map { children -> children.data }.toList()
    }
}
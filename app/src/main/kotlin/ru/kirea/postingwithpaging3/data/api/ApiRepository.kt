package ru.kirea.postingwithpaging3.data.api

import ru.kirea.postingwithpaging3.data.entities.PostData

/** Интерфейс работы с API */
interface ApiRepository {

    /**
     * Загрузить список постов.
     * @param after уникальный id поста, после которого нужно получить новый список постов.
     * @param before уникальный id поста, до которого нужно получить новый список постов.
     * @return список постов [PostData] или null, если нет постов
     */
    suspend fun getPosts(after: String? = null, before: String? = null): List<PostData>
}
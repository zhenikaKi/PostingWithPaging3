package ru.kirea.postingwithpaging3.data.db

import androidx.paging.PagingSource
import ru.kirea.postingwithpaging3.data.entities.PostData

/** Интерфейс работы с кешированными данными */
interface CacheRepository {
    /**
     * Сохранить посты.
     * @param posts список постов для сохранения.
     */
    suspend fun insertAll(posts: List<PostData>)

    /**
     * Получить список постов, которые были до определенной даты.
     * @return список постов, отсортированный по дате.
     */
    fun getPostsBeforeDate(): PagingSource<Int, PostData>

    /** Удалить все записи */
    suspend fun deleteAll()
}
package ru.kirea.postingwithpaging3.data.db

import androidx.paging.PagingSource
import ru.kirea.postingwithpaging3.data.entities.PostData

/** Кеширование данных в локальную БД */
class LocalCacheRepository(private val db: AppDB): CacheRepository {

    /**
     * Сохранить посты.
     * @param posts список постов для сохранения.
     */
    override suspend fun insertAll(posts: List<PostData>) {
        db.responseDataDao().insertAll(posts)
    }

    /**
     * Получить список постов, которые были до определенной даты.
     * @return список постов, отсортированный по дате.
     */
    override fun getPostsBeforeDate(): PagingSource<Int, PostData> =
        db.responseDataDao().getPostsBeforeDate()

    /** Удалить все записи */
    override suspend fun deleteAll() {
        db.responseDataDao().deleteAll()
    }
}
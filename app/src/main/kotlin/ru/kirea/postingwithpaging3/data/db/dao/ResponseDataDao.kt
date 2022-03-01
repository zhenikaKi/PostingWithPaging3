package ru.kirea.postingwithpaging3.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kirea.postingwithpaging3.data.db.DBConstants
import ru.kirea.postingwithpaging3.data.entities.PostData

/** Работа с локальной базой */
@Dao
interface ResponseDataDao {
    /**
     * Сохранить посты.
     * @param posts список постов для сохранения.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostData>)

    /**
     * Получить список постов, которые были до определенной даты.
     * @return список из постов, отсортированный по дате.
     */
    @Query("select * " +
            "from ${DBConstants.TABLE_CACHE} ")
    fun getPostsBeforeDate(): PagingSource<Int, PostData>

    /** Удалить все записи из базы */
    @Query("delete " +
            "from ${DBConstants.TABLE_CACHE} ")
    suspend fun deleteAll()
}
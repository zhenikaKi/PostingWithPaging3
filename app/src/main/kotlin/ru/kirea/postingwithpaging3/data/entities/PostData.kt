package ru.kirea.postingwithpaging3.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.kirea.postingwithpaging3.data.db.DBConstants

@Entity(tableName = DBConstants.TABLE_CACHE)
data class PostData (
    /** ID записи */
    @PrimaryKey
    @ColumnInfo(name = DBConstants.NAME_POST)
    @SerializedName("name")
    val name: String,

    /** заголовок поста */
    @ColumnInfo(name = DBConstants.TITLE)
    @SerializedName("title")
    val title: String,

    /** Дата поста */
    @ColumnInfo(name = DBConstants.CREATED)
    @SerializedName("created")
    val created: Long,

    /** Количество комментарив */
    @ColumnInfo(name = DBConstants.COUNT_COMMENTS)
    @SerializedName("num_comments")
    val comments: Int,

    /** Количество избранных */
    @ColumnInfo(name = DBConstants.COUNT_FAVORITES)
    @SerializedName("score")
    val favorites: Int
): Comparable<PostData> {

    /** Посты сортируем по их дате */
    override fun compareTo(other: PostData): Int {
        return created.compareTo(other.created)
    }
}
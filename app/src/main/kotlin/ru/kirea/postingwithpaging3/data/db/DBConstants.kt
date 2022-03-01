package ru.kirea.postingwithpaging3.data.db

/** Константы по базе данных */
object DBConstants {
    //база данных
    const val VERSION = 1
    const val NAME = "app.db"

    //таблицы
    const val TABLE_CACHE = "cache"

    //столбцы
    const val NAME_POST = "name_post"
    const val TITLE = "title"
    const val CREATED = "created"
    const val COUNT_COMMENTS = "count_comments"
    const val COUNT_FAVORITES = "count_favorites"
}
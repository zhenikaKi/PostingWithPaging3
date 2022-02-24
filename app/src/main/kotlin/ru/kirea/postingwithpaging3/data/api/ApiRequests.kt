package ru.kirea.postingwithpaging3.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kirea.postingwithpaging3.AppConstants
import ru.kirea.postingwithpaging3.data.entities.ResponseRoot

interface ApiRequests {
    /** Получить список постов */
    @GET("popular.json")
    suspend fun getPosts(@Query("limit") limit: Int = AppConstants.LIMIT_POSTS,
                         @Query("after") after: String? = null,
                         @Query("before") before: String? = null): ResponseRoot
}
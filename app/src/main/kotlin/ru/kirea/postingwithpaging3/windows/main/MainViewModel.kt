package ru.kirea.postingwithpaging3.windows.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import ru.kirea.postingwithpaging3.AppConstants
import ru.kirea.postingwithpaging3.data.api.ApiRepository
import ru.kirea.postingwithpaging3.data.db.CacheRepository
import ru.kirea.postingwithpaging3.data.entities.PostData
import ru.kirea.postingwithpaging3.windows.main.service.MainPostsMediator

@ExperimentalPagingApi
class MainViewModel(private val cacheRepository: CacheRepository,
                    private val apiRepository: ApiRepository): ViewModel() {

    /** Получить liveData по постам */
    fun getPostsLiveData(): LiveData<PagingData<PostData>> {
        val posts = { cacheRepository.getPostsBeforeDate() }

        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = posts,
            remoteMediator = MainPostsMediator(cacheRepository, apiRepository)
        ).liveData
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = AppConstants.LIMIT_POSTS, enablePlaceholders = false)
    }

}
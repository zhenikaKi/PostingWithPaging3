package ru.kirea.postingwithpaging3.windows.main.service

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Transaction
import ru.kirea.postingwithpaging3.AppConstants
import ru.kirea.postingwithpaging3.data.api.ApiRepository
import ru.kirea.postingwithpaging3.data.db.CacheRepository
import ru.kirea.postingwithpaging3.data.entities.PostData

@ExperimentalPagingApi
class MainPostsMediator(private val cacheRepository: CacheRepository,
                        private val apiRepository: ApiRepository): RemoteMediator<Int, PostData>() {

    /**
     * Загрузить данные для списка.
     * @param loadType вариант загрузки данных (первичная загрузка, добавление данных в конец или начало).
     * @param state информация о ранее загружаемых данных.
     * - На первом месте уникальный id страницы с постами.
     * - На втором - модель данных в виде поста [PostData].
     */
    override suspend fun load(loadType: LoadType, state: PagingState<Int, PostData>): MediatorResult {
        //получаем параметры страницы для загрузки постов из интернета
        val postId = when(val key = getKeyPage(loadType, state)) {
            is MediatorResult.Success -> return key
            else -> key as String?
        }

        //загружаем список постов из интеренета
        return loadPostFromInternet(postId, loadType)
    }

    /**
     * Получить ключ страницы или окончательный список успешного завершения постов.
     * @param loadType вариант загрузки данных (первичная загрузка, добавление данных в конец или начало).
     * @param state информация о ранее загружаемых данных.
     * - На первом месте уникальный id страницы с постами.
     * - На втором - модель данных в виде поста [PostData].
     */
    private fun getKeyPage(loadType: LoadType, state: PagingState<Int, PostData>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> { getClosestPosts(state) }
            LoadType.APPEND -> { getLastPosts(state) }
            LoadType.PREPEND -> {
                val key: String? = getFirstPosts(state)
                key?: return MediatorResult.Success(endOfPaginationReached = true)
                key
            }
        }
    }

    /**
     * Получить id первого поста из списка.
     * @param state информация о ранее загружаемых данных.
     * - На первом месте уникальный id страницы с постами.
     * - На втором - модель данных в виде поста [PostData].
     * @return id поста для запроса в api или null при пустом списке постов.
     */
    private fun getFirstPosts(state: PagingState<Int, PostData>): String? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()?.name
    }

    /**
     * Получить id последнего поста из списка.
     * @param state информация о ранее загружаемых данных.
     * - На первом месте уникальный id страницы с постами.
     * - На втором - модель данных в виде поста [PostData].
     * @return id поста для запроса в api или null при пустом списке постов.
     */
    private fun getLastPosts(state: PagingState<Int, PostData>): String? {
        return state.pages
            .lastOrNull() { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()?.name
    }

    /**
     * Получить id ближайшего поста.
     * @param state информация о ранее загружаемых данных.
     * - На первом месте уникальный id страницы с постами.
     * - На втором - модель данных в виде поста [PostData].
     * @return id поста для запроса в api или null при пустом списке постов.
     */
    private fun getClosestPosts(state: PagingState<Int, PostData>): String? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name
        }
    }

    /**
     * Загрузить список постов из интернета и сохранить их в базу.
     * @param postId идентификатор поста, от которого или до которого нужно загрузить посты.
     * @param loadType вариант загрузки данных (первичная загрузка, добавление данных в конец или начало).
     */
    @Transaction
    private suspend fun loadPostFromInternet(postId: String?, loadType: LoadType): MediatorResult {
        return try {
            val asAfter = loadType == LoadType.APPEND
            val after: String? = if (asAfter) postId else null
            val before: String? = if (!asAfter) postId else null
            val posts: List<PostData> = getPosts(after, before)
            Log.d(AppConstants.TAG_LOG, "posts ${posts.map { p -> "${p.title}\n" }}")
            val emptyPosts: Boolean = posts.isEmpty()
            if (loadType == LoadType.REFRESH) {
                cacheRepository.deleteAll()
            }
            if (!emptyPosts) {
                cacheRepository.insertAll(posts)
            }

            MediatorResult.Success(endOfPaginationReached = emptyPosts)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    /**
     * Получить информацию по постам.
     * @param after уникальный id поста, после которого нужно получить новый список постов.
     * @param before уникальный id поста, до которого нужно получить новый список постов.
     * @return осортированный по дате поста список постов или null, если постов нет.
     */
    private suspend fun getPosts(after: String? = null, before: String? = null): List<PostData> {
        return apiRepository.getPosts(after = after, before = before)
    }
}
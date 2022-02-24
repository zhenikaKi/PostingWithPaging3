package ru.kirea.postingwithpaging3.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.kirea.postingwithpaging3.data.api.ApiRepository
import ru.kirea.postingwithpaging3.data.api.ApiRequests
import ru.kirea.postingwithpaging3.data.api.RedditApiRepository
import ru.kirea.postingwithpaging3.data.api.RetrofitHelper
import ru.kirea.postingwithpaging3.data.db.AppDB
import ru.kirea.postingwithpaging3.data.db.CacheRepository
import ru.kirea.postingwithpaging3.data.db.DBConstants
import ru.kirea.postingwithpaging3.data.db.LocalCacheRepository
import ru.kirea.postingwithpaging3.windows.main.MainFragment
import ru.kirea.postingwithpaging3.windows.main.MainViewModel

object Modules {
    //модуль, содержимое которого должно быть во всем приложении
    val application = module {
        //база данных
        single<AppDB>(qualifier = named(Scopes.DB)) {
            Room.databaseBuilder(get(), AppDB::class.java, DBConstants.NAME)
                .build()
        }
        single<CacheRepository>(qualifier = named(Scopes.DB_REPOSITORY)) {
            LocalCacheRepository(get(qualifier = named(Scopes.DB)))
        }

        //работа с api
        single<Retrofit>(qualifier = named(Scopes.RETROFIT)) {
            RetrofitHelper.create()
        }
        single<ApiRequests>(qualifier = named(Scopes.API)) {
            get<Retrofit>(qualifier = named(Scopes.RETROFIT)).create(ApiRequests::class.java)
        }
        single<ApiRepository>(qualifier = named(Scopes.API_REPOSITORY)) {
            RedditApiRepository(get(qualifier = named(Scopes.API)))
        }
    }

    //модуль основного экрана
    @ExperimentalPagingApi
    val mainWindow = module {
        scope<MainFragment> {
            viewModel(qualifier = named(Scopes.MAIN_VIEW_MODEL)) {
                MainViewModel(
                    get(qualifier = named(Scopes.DB_REPOSITORY)),
                    get(qualifier = named(Scopes.API_REPOSITORY)))
            }
        }
    }
}

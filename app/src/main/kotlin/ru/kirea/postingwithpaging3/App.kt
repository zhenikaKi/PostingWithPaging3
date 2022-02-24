package ru.kirea.postingwithpaging3

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kirea.postingwithpaging3.di.Modules

@ExperimentalPagingApi
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                Modules.application,
                Modules.mainWindow,
            )
        }
    }
}
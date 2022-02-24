package ru.kirea.postingwithpaging3.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kirea.postingwithpaging3.data.db.dao.ResponseDataDao
import ru.kirea.postingwithpaging3.data.entities.PostData

@Database(
    entities = [
        PostData::class
    ],
    version = DBConstants.VERSION,
    exportSchema = true)
abstract class AppDB: RoomDatabase() {
    abstract fun responseDataDao(): ResponseDataDao
}
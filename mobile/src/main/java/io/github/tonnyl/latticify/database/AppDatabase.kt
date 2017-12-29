package io.github.tonnyl.latticify.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.tonnyl.latticify.data.AccessToken
import io.github.tonnyl.latticify.database.daos.AccessTokenDao

/**
 * Created by lizhaotailang on 15/10/2017.
 */
@Database(entities = [(AccessToken::class)], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @JvmStatic
        private val DATABASE_NAME = "latticify-db"

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME)
                            .build()
                }
                return INSTANCE as AppDatabase
            }
        }
    }

    abstract fun accessTokenDao(): AccessTokenDao

}
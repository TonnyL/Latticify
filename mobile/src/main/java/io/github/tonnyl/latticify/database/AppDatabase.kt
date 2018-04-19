package io.github.tonnyl.latticify.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.database.daos.UserPoolDao

/**
 * Created by lizhaotailang on 15/10/2017.
 */
@Database(entities = [(User::class)], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        @JvmStatic
        private val DATABASE_NAME = "latticify-db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: (Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME)
                    .build()).also { INSTANCE = it }
        }

    }

    abstract fun userPoolDao(): UserPoolDao

}
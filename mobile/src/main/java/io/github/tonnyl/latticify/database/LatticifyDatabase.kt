package io.github.tonnyl.latticify.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.tonnyl.latticify.data.AccessToken
import io.github.tonnyl.latticify.database.daos.AccessTokenDao

/**
 * Created by lizhaotailang on 15/10/2017.
 */
@Database(entities = arrayOf(AccessToken::class),
        version = 1)
abstract class LatticifyDatabase : RoomDatabase() {

    abstract fun accessTokenDao(): AccessTokenDao

}
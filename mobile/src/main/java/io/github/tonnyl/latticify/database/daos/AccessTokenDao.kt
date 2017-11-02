package io.github.tonnyl.latticify.database.daos

import android.arch.persistence.room.*
import io.github.tonnyl.latticify.data.AccessToken
import io.reactivex.Flowable

/**
 * Created by lizhaotailang on 15/10/2017.
 */
@Dao
interface AccessTokenDao {

    @Query("SELECT * FROM access_tokens")
    fun queryAll(): Flowable<List<AccessToken>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accessToken: AccessToken): Long

    @Update
    fun update(accessToken: AccessToken)

    @Delete
    fun delete(accessToken: AccessToken)

}
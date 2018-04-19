package io.github.tonnyl.latticify.database.daos

import android.arch.persistence.room.*
import io.github.tonnyl.latticify.data.User
import io.reactivex.Flowable

@Dao
interface UserPoolDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<User>)

    @Delete
    fun removeUser(user: User)

    @Delete
    fun removeUsers(users: List<User>)

    @Query("SELECT * FROM user where id = :userId")
    fun getUser(userId: String): Flowable<User>

    @Update
    fun updateUser(user: User)

}
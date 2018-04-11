package io.github.tonnyl.latticify.database.daos

import android.arch.persistence.room.*
import io.github.tonnyl.latticify.data.User

@Dao
interface DirectoryDao {

    @Query("SELECT * FROM directory where id = :id")
    fun querySingleById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Delete
    fun deleteById(id: String)

}
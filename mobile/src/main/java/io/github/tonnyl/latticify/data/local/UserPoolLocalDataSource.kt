package io.github.tonnyl.latticify.data.local

import android.content.Context
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.datasource.UserPoolDataSource
import io.github.tonnyl.latticify.database.AppDatabase
import io.reactivex.Observable

object UserPoolLocalDataSource : UserPoolDataSource {

    private lateinit var mDb: AppDatabase

    override fun init(context: Context) {
        mDb = AppDatabase.getInstance(context)
    }

    override fun addUser(user: User) {
        mDb.userPoolDao().addUser(user)
    }

    override fun addUsers(users: List<User>) {
        mDb.userPoolDao().addUsers(users)
    }

    override fun removeUser(user: User) {
        mDb.userPoolDao().removeUser(user)
    }

    override fun removeUsers(users: List<User>) {
        mDb.userPoolDao().removeUsers(users)
    }

    override fun getUser(userId: String): Observable<User> {
        return mDb.userPoolDao().getUser(userId).toObservable()
    }

    override fun updateUser(user: User) {
        mDb.userPoolDao().updateUser(user)
    }

}
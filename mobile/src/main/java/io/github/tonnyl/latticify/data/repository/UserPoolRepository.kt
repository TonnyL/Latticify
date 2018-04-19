package io.github.tonnyl.latticify.data.repository

import android.content.Context
import io.github.tonnyl.latticify.data.User
import io.github.tonnyl.latticify.data.datasource.UserPoolDataSource
import io.github.tonnyl.latticify.data.local.UserPoolLocalDataSource
import io.github.tonnyl.latticify.data.remote.UserPoolRemoteDataSource
import io.reactivex.Observable

object UserPoolRepository : UserPoolDataSource {

    private val mUserPool = mutableMapOf<String, User>()

    override fun init(context: Context) {
        UserPoolLocalDataSource.init(context)
    }

    override fun addUser(user: User) {
        mUserPool[user.id] = user

        UserPoolLocalDataSource.addUser(user)
    }

    override fun addUsers(users: List<User>) {
        users.forEach {
            mUserPool[it.id] = it
        }

        UserPoolLocalDataSource.addUsers(users)
    }

    override fun removeUser(user: User) {
        mUserPool.remove(user.id)

        UserPoolLocalDataSource.removeUser(user)
    }

    override fun removeUsers(users: List<User>) {
        users.forEach {
            mUserPool.remove(it.id)
        }

        UserPoolLocalDataSource.removeUsers(users)
    }

    override fun getUser(userId: String): Observable<User> {
        // from cache
        if (mUserPool[userId] != null) {
            return Observable.just(mUserPool[userId])
        }

        return UserPoolRemoteDataSource.getUser(userId)
                .doOnNext {
                    it?.let {
                        // put it into cache
                        mUserPool[it.id] = it

                        // put it into database
                        UserPoolLocalDataSource.addUser(it)
                    }
                }
        // from database or network
//        return UserPoolLocalDataSource.getUser(userId)
//                .doOnNext {
//                    it?.let {
//                        mUserPool[it.id] = it
//                    }
//                } ?:
    }

    override fun updateUser(user: User) {
        // put user into map or just update it.
        mUserPool[user.id] = user

        UserPoolLocalDataSource.updateUser(user)
    }

}
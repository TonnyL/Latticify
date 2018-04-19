package io.github.tonnyl.latticify.data.datasource

import android.content.Context
import io.github.tonnyl.latticify.data.User
import io.reactivex.Observable

interface UserPoolDataSource {

    fun init(context: Context)

    fun addUser(user: User)

    fun addUsers(users: List<User>)

    fun removeUser(user: User)

    fun removeUsers(users: List<User>)

    fun getUser(userId: String): Observable<User>

    fun updateUser(user: User)

}
package io.github.tonnyl.latticify.data.datasource

import io.github.tonnyl.latticify.data.User

interface UserPoolDataSource {

    fun addUser(user: User)

    fun addUsers(users: List<User>)

    fun removeUser(userId: String)

    fun removeUsers(userIds: List<User>)

    fun removeAll()

    fun getUser(userId: String)

    fun updateUser(user: User)

}